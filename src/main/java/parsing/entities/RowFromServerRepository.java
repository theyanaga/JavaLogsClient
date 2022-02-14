package parsing.entities;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
@Transactional
public class RowFromServerRepository implements PanacheRepository<RowFromServer> {
    
    public Map<User, List<String>> findCsvLineByUserMap() {
        return getEntityManager()
            .createQuery("from RowFromServer", RowFromServer.class)  
            .getResultStream()
            .collect(Collectors.toMap(
                tuple -> tuple.getUser(), 
                tuple -> List.of(tuple.createCSVLineFromRow()),
                (t1, t2) -> Stream.of(t1, t2)
                    .flatMap(Collection::stream)
                    .collect(Collectors.toList())));
    }

}
