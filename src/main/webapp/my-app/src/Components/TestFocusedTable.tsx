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
              <TableCell align="center"> 
                {result.usersThatPassed && result.usersThatPassed.length > 0 ? result.usersThatPassed[0].firstName + " " + result.usersThatPassed[0].lastName + ", " : ""}
                {result.usersThatPassed && result.usersThatPassed.length > 1 ? result.usersThatPassed[1].firstName + " " + result.usersThatPassed[1].lastName + ", ": ""}
                {result.usersThatPassed && result.usersThatPassed.length > 2 ? result.usersThatPassed[2].firstName + " " + result.usersThatPassed[2].lastName : ""}
                ({result.numberOfUsersThatPassed})
              </TableCell>
              <TableCell align="center"> 
                {result.usersThatPartiallyPassed && result.usersThatPartiallyPassed.length > 0 ? result.usersThatPartiallyPassed[0].firstName + " " + result.usersThatPartiallyPassed[0].lastName + ", " : ""}
                {result.usersThatPartiallyPassed && result.usersThatPartiallyPassed.length > 1 ? result.usersThatPartiallyPassed[1].firstName + " " + result.usersThatPartiallyPassed[1].lastName + ", ": ""}
                {result.usersThatPartiallyPassed && result.usersThatPartiallyPassed.length > 2 ? result.usersThatPartiallyPassed[2].firstName + " " + result.usersThatPartiallyPassed[2].lastName : ""}
                ({result.numberOfUsersThatPartiallyPassed})
              </TableCell>
              <TableCell align="center"> 
                {result.usersThatFailed && result.usersThatFailed.length > 0 ? result.usersThatFailed[0].firstName + " " + result.usersThatFailed[0].lastName + ", ": ""}
                {result.usersThatFailed && result.usersThatFailed.length > 1 ? result.usersThatFailed[1].firstName + " " + result.usersThatFailed[1].lastName + ", ": ""}
                {result.usersThatFailed && result.usersThatFailed.length > 2 ? result.usersThatFailed[2].firstName + " " + result.usersThatFailed[2].lastName : ""}
                ({result.numberOfUsersThatFailed})
              </TableCell>
              <TableCell align="center"> 
                {result.usersThatDidNoRunTest && result.usersThatDidNoRunTest.length > 0 ? result.usersThatDidNoRunTest[0].firstName + " " + result.usersThatDidNoRunTest[0].lastName + ", ": ""}
                {result.usersThatDidNoRunTest && result.usersThatDidNoRunTest.length > 1 ? result.usersThatDidNoRunTest[1].firstName + " " + result.usersThatDidNoRunTest[1].lastName + ", ": ""}
                {result.usersThatDidNoRunTest && result.usersThatDidNoRunTest.length > 2 ? result.usersThatDidNoRunTest[2].firstName + " " + result.usersThatDidNoRunTest[2].lastName : ""}
                ({result.numberOfUsersThatDidNotRunTest})
              </TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  );
}
