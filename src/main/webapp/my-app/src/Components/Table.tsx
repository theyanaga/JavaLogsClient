import * as React from "react";
import Table from "@mui/material/Table";
import TableBody from "@mui/material/TableBody";
import TableCell from "@mui/material/TableCell";
import TableContainer from "@mui/material/TableContainer";
import TableHead from "@mui/material/TableHead";
import TableRow from "@mui/material/TableRow";
import Paper from "@mui/material/Paper";
import { useState } from "react";

function createData(
  name: string,
  calories: number,
  fat: number,
  carbs: number,
  protein: number
) {
  return { name, calories, fat, carbs, protein };
}

export interface Course {
  id: number;
  className: string;
  year: number;
}

export interface Assignment {
    id: number;
    number: number;
    course: Course;
}

export interface User {
    id: number;
    machineId: string;
    firstName: string;
    lastName: string;
    fullName: string;
}

export interface UserWithTests{
    user: User;
    sumOfScores: number;
    tests: LocalTest[]
}

export interface TestName {
    id: number;
    name: string;
}

export interface LocalTest {
  assignment: Assignment;
  attempts: number;
  name: TestName;
  points: number;
  totalPoints: number;
  status: string;
}

/*

Use TestResult as rows. 

Columns are: 

      uid | passed | partial | failed | untested

For each field other than uid, make the value the uid of the first three users, 
and then in parenthesis add the total number of users that passed/failed that test. 

e.g 1, 53, 74 ... (15)

*/
export default function TestTable(this: any) {
  const [usersWithTests, setUsersWithTests] = useState<UserWithTests[]>([]);
  const [testNames, setTestNames] = useState([]);
  const [areTestsLoading, setAreTestsLoading] = useState(true);
  const [areTestNamesLoading, setAreTestNamesLoading] = useState(true);

  function getDataFromServer() {
     fetch("http://localhost:8080/potato/allUsers", {
      method: "GET",
    })
      .then((response) => response.json())
      .then((responseData) => {
        setUsersWithTests(responseData);
        setAreTestsLoading(false)
      });
  }
  
  function getTestNames() {
     fetch("http://localhost:8080/potato/salad", {
      method: "GET",
    })
      .then((response) => response.json())
      .then((responseData) => {
        setTestNames(responseData);
        setAreTestNamesLoading(false);
      });

  }

  React.useEffect(getDataFromServer, []) // Runs everytime a state changes
  React.useEffect(getTestNames, []) // Runs everytime a state changes


  console.log(usersWithTests)

  if (areTestNamesLoading || areTestsLoading) {
      return <div className="App">Loading...</div>;
  }
  return (
    <TableContainer component={Paper}>
      <Table sx={{ minWidth: 10 }} aria-label="simple table">
        <TableHead>
                <TableRow>
                    <TableCell> User </TableCell>
                    {testNames.map((testName) => (
                        <TableCell key={testName}>
                            {testName}
                        </TableCell>
                    ))}
                </TableRow>
         </TableHead>
        <TableBody>
          {usersWithTests.map((userWithTest) => (
            <TableRow
              sx={{ "&:last-child td, &:last-child th": { border: 0 } }}
            >
              <TableCell component="th" scope="row">
                {userWithTest.user.firstName + " " + userWithTest.user.lastName}
              </TableCell>
              {userWithTest.tests.map((test) => (
                <TableCell align="center">{test.status}</TableCell>
              ))}
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  );
}
