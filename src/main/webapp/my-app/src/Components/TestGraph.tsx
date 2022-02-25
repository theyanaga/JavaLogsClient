import React, { PureComponent, useState } from 'react';
import { BarChart, Bar, Cell, XAxis, YAxis, CartesianGrid, Tooltip, Legend, ResponsiveContainer } from 'recharts';
import { convertCompilerOptionsFromJson } from 'typescript';

interface UserBarGraphEntity {
    testName: string;
    countOfPassed: number;
    countOfPartiallyPassed: number;
    countOfFailed: number;
    countOfUntested: number;
}

enum DisplaySelected {
    ByName,
    Ascending, 
    Descending,
}

export default function Example() {
  const [userBarGraphEntity, setUserBarGraphEntity] = useState<UserBarGraphEntity[]>([]);
  const [areTestsLoading, setAreTestsLoading] = useState(true);
  const [displayedData, setDisplayedData] = useState<UserBarGraphEntity[]>([]);
  
  function getDataFromServer() {
     fetch("http://localhost:8080/graphs/testsPassedBarGraphSortedByName", {
      method: "GET",
    })
      .then((response) => response.json())
      .then((responseData) => {
        setUserBarGraphEntity(responseData);
        setDisplayedData(responseData);
        setAreTestsLoading(false)
      });
  }

  React.useEffect(getDataFromServer, []) // Runs everytime a state changes

  console.log(userBarGraphEntity)


  function setByName() {
      setDisplayedData(userBarGraphEntity);
  }

  function setDescending() {
      const copyOfArray = userBarGraphEntity.map((x) => x);
      setDisplayedData(copyOfArray.sort((a,b) => b.countOfPassed - a.countOfPassed));
  }

  function setAscending(){
      const copyOfArray = userBarGraphEntity.map((x) => x);
      setDisplayedData(copyOfArray.sort((a,b) => a.countOfPassed - b.countOfPassed));
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
                    <YAxis dataKey="testName" type="category" width={300}/>
                    <Tooltip />
                    <Legend verticalAlign='top' wrapperStyle={{paddingBottom:'10px', paddingLeft: '140px'}}/>
                    <Bar name="# Passed" dataKey="countOfPassed" stackId="a" fill="#82ca9d"/>
                    <Bar name="# Partial" dataKey="countOfPartiallyPassed" stackId="a" fill="#F5D874" />
                    <Bar name="# Failed" dataKey="countOfFailed" stackId="a" fill="#F3937A" />
                    <Bar name="# Untested" dataKey="countOfUntested" stackId="a" fill="grey"/>
                    </BarChart>
                </div>
            </div>
        );
    }
}
