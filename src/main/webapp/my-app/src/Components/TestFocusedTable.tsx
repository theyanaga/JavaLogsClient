import { TableContainer, Paper, Table, TableHead, TableRow, TableCell, TableBody } from "@mui/material";
import React from "react";
import { useState } from "react";
import { User, UserWithTests } from "./Table";


interface AccumulatedResultByTest {
  name: string;
  usersThatPassed: User[];
  usersThatFailed: User[];
  usersThatPartiallyPassed: User[];
  usersThatDidNoRunTest: User[];
  numberOfUsersThatPassed: number;
  numberOfUsersThatFailed: number;
  numberOfUsersThatPartiallyPassed: number;
  numberOfUsersThatDidNotRunTest: number;
}

export default function TestFocusedTable(this: any) {
//   const [testNames, setTestNames] = useState([]);
  const [accumulatedResultByTest, setAccumulatedResultByTest] = useState<AccumulatedResultByTest[]>([]);
  const [areTestsLoading, setAreTestsLoading] = useState(true);

  function getDataFromServer() {
     fetch("http://localhost:8080/tests", {
      method: "GET",
    })
      .then((response) => response.json())
      .then((responseData) => {
        setAccumulatedResultByTest(responseData);
        setAreTestsLoading(false)
      });
  }




  React.useEffect(getDataFromServer, []) // Runs everytime a state changes


  console.log(accumulatedResultByTest)

  if (areTestsLoading) {
      return <div className="App">Loading...</div>;
  }
  return (
    <TableContainer component={Paper}>
      <Table sx={{ minWidth: 10 }} aria-label="simple table">
        <TableHead>
                <TableRow>
                    <TableCell align="center">Test Name</TableCell>
                    <TableCell align="center">Passed</TableCell> 
                    <TableCell align="center">Partially Passed</TableCell> 
                    <TableCell align="center">Fail</TableCell> 
                    <TableCell align="center">Untested</TableCell> 
                </TableRow>
         </TableHead>
        <TableBody>
          {accumulatedResultByTest.map((result) => (
            <TableRow
              sx={{ "&:last-child td, &:last-child th": { border: 0 } }}
            >
              <TableCell component="th" scope="row" align="center">
                {result.name}
              </TableCell>
              <TableCell align="center">{result.numberOfUsersThatPassed}</TableCell>
              <TableCell align="center">{result.numberOfUsersThatPartiallyPassed}</TableCell>
              <TableCell align="center">{result.numberOfUsersThatFailed}</TableCell>
              <TableCell align="center">{result.numberOfUsersThatDidNotRunTest}</TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  );
}
