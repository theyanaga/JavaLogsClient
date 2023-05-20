import React, { PureComponent, useState } from 'react';
import { BarChart, Bar, Cell, XAxis, YAxis, CartesianGrid, Tooltip, Legend, ResponsiveContainer } from 'recharts';
import { convertCompilerOptionsFromJson } from 'typescript';
import { User, UserWithTests } from './Table';

interface UserBarGraphEntity {
    testName: string;
    countOfPassed: number;
    countOfPartiallyPassed: number;
    countOfFailed: number;
    countOfUntested: number;
}

export default function ScoreGraph() {
  const [userWithTests, setUserWithTests] = useState<UserWithTests[]>([]);
  const [areTestsLoading, setAreTestsLoading] = useState(true);
  const [displayedData, setDisplayedData] = useState<UserWithTests[]>([]);

  function concatName(user: User) {
      console.log(user)
      return user.firstName;
    
  }
  
  function getDataFromServer() {
     fetch("http://localhost:8080/scores", {
      method: "GET",
    })
      .then((response) => response.json())
      .then((responseData) => {
        setUserWithTests(responseData);
        setDisplayedData(responseData);
        setAreTestsLoading(false)
      });
  }

  React.useEffect(getDataFromServer, []) // Runs everytime a state changes

  console.log(userWithTests);

  function setByName() {
      setDisplayedData(userWithTests);
  }

  function setDescending() {
      const copyOfArray = userWithTests.map((x) => x);
      setDisplayedData(copyOfArray.sort((a,b) => b.sumOfScores - a.sumOfScores));
  }

  function setAscending(){
      const copyOfArray = userWithTests.map((x) => x);
      setDisplayedData(copyOfArray.sort((a,b) => a.sumOfScores - b.sumOfScores));
  }

  if (areTestsLoading) {
      return <div className="App">Loading...</div>;
  }
  else {
      return (
          <div>
                <div style={{display: 'flex', justifyContent:'center'}}>
                    <div>
                        <button onClick={setAscending}>Ascending</button>
                    </div>
                    <div>
                        <button onClick={setDescending}>Descending</button>
                    </div>
                    <div>
                        <button onClick={setByName}>By Name</button>
                    </div>
                </div>
                <div style={{display: 'flex',  justifyContent:'center', alignItems:'center'}}>
                    <BarChart
                    layout='vertical'
                    width={900}
                    height={displayedData.length * 50}
                    data={displayedData}
                    margin={{
                        top: 20,
                        right: 30,
                        left: 30,
                        bottom: 5,
                    }}
                    >
                    <CartesianGrid strokeDasharray="3 3" />
                    <XAxis type="number"/>
                    <YAxis dataKey="user.fullName" type="category" width={300}/>
                    <Tooltip />
                    <Legend verticalAlign='top' wrapperStyle={{paddingBottom:'10px', paddingLeft: '140px'}}/>
                    <Bar name="Score" dataKey="sumOfScores" stackId="a" fill="grey"/>
                    </BarChart>
                </div>
            </div>
        );
    }
}
