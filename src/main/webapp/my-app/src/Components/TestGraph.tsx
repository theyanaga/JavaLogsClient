import React, { PureComponent, useState } from 'react';
import { BarChart, Bar, Cell, XAxis, YAxis, CartesianGrid, Tooltip, Legend, ResponsiveContainer } from 'recharts';

interface UserBarGraphEntity {
    testName: string;
    countOfPassed: number;
    countOfPartiallyPassed: number;
    countOfFailed: number;
    countOfUntested: number;

}

const data = [
  {
    name: 'Page A',
    uv: 4000,
    pv: 2400,
    amt: 2400,
  },
  {
    name: 'Page B',
    uv: 3000,
    pv: 1398,
    amt: 2210,
  },
  {
    name: 'Page C',
    uv: 2000,
    pv: 9800,
    amt: 2290,
  },
  {
    name: 'Page D',
    uv: 2780,
    pv: 3908,
    amt: 2000,
  },
  {
    name: 'Page E',
    uv: 1890,
    pv: 4800,
    amt: 2181,
  },
  {
    name: 'Page F',
    uv: 2390,
    pv: 3800,
    amt: 2500,
  },
  {
    name: 'Page G',
    uv: 3490,
    pv: 4300,
    amt: 2100,
  },
];

export default function Example() {
  const [userBarGraphEntity, setUserBarGraphEntity] = useState<UserBarGraphEntity[]>([]);
  const [areTestsLoading, setAreTestsLoading] = useState(true);
  
  function getDataFromServer() {
     fetch("http://localhost:8080/graphs/testsPassedBarGraph", {
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
                <Bar name="# Untested" dataKey="countOfUntested" stackId="a" fill="grey" />
                </BarChart>
            </div>
    );
}
