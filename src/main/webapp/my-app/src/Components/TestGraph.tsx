import React, { PureComponent, useState } from 'react';
import { BarChart, Bar, Cell, XAxis, YAxis, CartesianGrid, Tooltip, Legend, ResponsiveContainer } from 'recharts';

interface UserBarGraphEntity {
    testName: string;
    countOfPassed: number;
    countOfPartiallyPassed: number;
    countOfFailed: number;
    countOfUntested: number;

}

export default function Example() {
  const [userBarGraphEntity, setUserBarGraphEntity] = useState<UserBarGraphEntity[]>([]);
  const [areTestsLoading, setAreTestsLoading] = useState(true);
  
  function getDataFromServer() {
     fetch("http://localhost:8080/graphs/testsPassedBarGraphDescendingOrders", {
      method: "GET",
    })
      .then((response) => response.json())
      .then((responseData) => {
        setUserBarGraphEntity(responseData);
        setAreTestsLoading(false)
      });
  }

  React.useEffect(getDataFromServer, []) // Runs everytime a state changes

  console.log(userBarGraphEntity)

  if (areTestsLoading) {
      return <div className="App">Loading...</div>;
  }
      return (
            <div style={{display: 'flex',  justifyContent:'center', alignItems:'center'}}>
                <BarChart
                layout='vertical'
                width={900}
                height={userBarGraphEntity.length * 50}
                data={userBarGraphEntity}
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
    );
}
