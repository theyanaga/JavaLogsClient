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
    user_id: number;
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
  const [isLoading, setIsLoading] = useState(true);

  function getDataFromServer() {
     fetch("http://localhost:8080/potato", {
      method: "GET",
    })
      .then((response) => response.json())
      .then((responseData) => {
        setUsersWithTests(responseData);
        setIsLoading(false)
      });
  }

  React.useEffect(getDataFromServer, []) // Runs everytime a state changes

  console.log(usersWithTests)

  if (isLoading) {
      return <div className="App">Loading...</div>;
  }
  return (
    <TableContainer component={Paper}>
      <Table sx={{ minWidth: 10 }} aria-label="simple table">
        <TableHead>
                <TableRow>
                    <TableCell> User </TableCell>
                {usersWithTests.map((userWithTest) => ( // This will not work because we have already iterated through the first object
                    userWithTest.tests.map((test) => (
                        <TableCell scope="test">
                            {test.name}
                        </TableCell>
                    ))
                ))}
                </TableRow>
         </TableHead>
        {/* <TableBody>
          {rows.map((row) => (
            <TableRow
              key={row.name}
              sx={{ "&:last-child td, &:last-child th": { border: 0 } }}
            >
              <TableCell component="th" scope="row">
                {row.name}
              </TableCell>
              <TableCell align="right">{row.calories}</TableCell>
              <TableCell align="right">{row.fat}</TableCell>
              <TableCell align="right">{row.carbs}</TableCell>
              <TableCell align="right">{row.protein}</TableCell>
            </TableRow>
          ))}
        </TableBody> */}
      </Table>
    </TableContainer>
  );
}
