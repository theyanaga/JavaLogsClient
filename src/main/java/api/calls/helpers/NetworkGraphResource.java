package api.calls.helpers;

import gradingTools.logs.localChecksStatistics.collectors.Collector;
import parsing.entities.*;
import parsing.entities.projections.GraphEdge;

import javax.persistence.ColumnResult;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Path("/network-graph")
public class NetworkGraphResource {

    @GET
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    public SimpleGraph getNetworkGraphForUser() {

        List<Session> userSessions = Session.getEntityManager().createQuery("select s  from User u inner join Session s on u.id = s.userId where s.userId =: userId", Session.class).setParameter("userId", (long) 119)
                .getResultList();

        userSessions.sort(Comparator.comparing(Session::getSessionId));

        TypedQuery<LocalTest> testsInSessionQuery = LocalTest.getEntityManager().createQuery("select lt from LocalTest lt inner join TestAndSession ts on ts.testId = lt.id inner join Session s on s.id = ts.sessionId where s.sessionId =: sessionId and lt.userId =: userId and lt.status =: status", LocalTest.class)
                .setParameter("status", TestStatus.PASS);

        testsInSessionQuery.setParameter("userId", (long) 119);

        List<SimpleNode> nodes = new ArrayList<>();

        List<SimpleEdge> edges = new ArrayList<>();

        for (Session session : userSessions) {

            List<LocalTest> testsInSession = testsInSessionQuery.setParameter("sessionId", session.getSessionId()).setParameter("userId", (long) 119).getResultList();

//            GraphNode node = new GraphNode(testsInSession, session.getSessionId(), User.findById((long)119), session.getTime());

            SimpleNode node = new SimpleNode(session.getSessionId(), session.getTime());

            for (LocalTest test : testsInSession) {
                node.addTestId(test);
            }

            if (nodes.size() > 0) {
                edges.add(new SimpleEdge(nodes.get(nodes.size() - 1), node));
            }

            nodes.add(node);
        }

        List<User> users = User.getEntityManager().createQuery("select distinct u from LocalTest t inner join User u on t.userId = u.id where t.assignmentId =:assignment", User.class)
                .setParameter("assignment", (long)53584).getResultList();

        for (User user : users) {
            if (user.id != (long) 119) {
                testsInSessionQuery.setParameter("userId", user.id);

                for (Session session : Session.getEntityManager().createQuery("select s  from User u inner join Session s on u.id = s.userId where s.userId =: userId", Session.class)
                        .setParameter("userId", user.id).getResultList()) {
                    List<LocalTest> testsInSession = testsInSessionQuery.setParameter("sessionId", session.getSessionId()).setParameter("userId", (long) 119).getResultList();
                    SimpleNode node = new SimpleNode(session.getSessionId(), session.getTime());

                    for (LocalTest test : testsInSession) {
                        node.addTestId(test);
                    }

                    for (SimpleNode simpleNode : nodes) {
                        Collections.sort(simpleNode.getTestIds());
                        Collections.sort(node.getTestIds());
                        if (simpleNode.getTestIds().equals(node.getTestIds())) {
                            simpleNode.addCollision(user.id);
                        }
                    }
                }
            }
        }

        return new SimpleGraph(nodes, edges);
    }

}
