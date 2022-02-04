import * as React from "react";
import Table from "@mui/material/Table";
import TableBody from "@mui/material/TableBody";
import TableCell from "@mui/material/TableCell";
import TableContainer from "@mui/material/TableContainer";
import TableHead from "@mui/material/TableHead";
import TableRow from "@mui/material/TableRow";
import Paper from "@mui/material/Paper";
import { useState } from "react";
import { createObjectBindingPattern } from "typescript";

function createData(
  name: string,
  calories: number,
  fat: number,
  carbs: number,
  protein: number
) {
  return { name, calories, fat, carbs, protein };
}

interface User {
    id: number;
    machineId: string;
}

interface UserWithTests{
    user: User;
    tests: LocalCheckTest[]
}

interface LocalCheckTest {
  attempts: number;
  hasPoints: boolean;
  name: string;
  points: number;
  totalPoints: number;
  status: string;
}

const rows = [
  createData("Frozen yoghurt", 159, 6.0, 24, 4.0),
  createData("Ice cream sandwich", 237, 9.0, 37, 4.3),
  createData("Eclair", 262, 16.0, 24, 6.0),
  createData("Cupcake", 305, 3.7, 67, 4.3),
  createData("Gingerbread", 356, 16.0, 49, 3.9),
];

export default function TestTable(this: any) {
//   const [testNames, setTestNames] = useState([]);
  const [usersWithTests, setUsersWithTests] = useState<UserWithTests[]>([]);
  const [testNames, setTestNames] = useState([]);
  const [areTestsLoading, setAreTestsLoading] = useState(true);
  const [areTestNamesLoading, setAreTestNamesLoading] = useState(true);

  function getDataFromServer() {
     fetch("http://localhost:8080/potato", {
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
                {userWithTest.user.id}
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
