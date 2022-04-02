import './App.css';
import {Route, BrowserRouter, Routes} from 'react-router-dom'
import Table from './Components/Table';
import TestFocusedTable from './Components/TestFocusedTable';
import Home from './Components/Pages/Home';
import Example from './Components/TestGraph';
import ScoreGraph from './Components/ScoreGraph';
import NetworkGraph from './Components/NetworkGraph';

function App() {
  return (
   <BrowserRouter>
    <Routes>
      <Route path="/" element={<Home />}>
        <Route path="table" element={<Table/>}/>
        <Route path="testFocusedTable" element={<TestFocusedTable/>}/>
        <Route path="graph" element={<Example/>}/>
        <Route path="score" element={<ScoreGraph/>}/>
        <Route path="network" element={<NetworkGraph/>}/>
      </Route> 
    </Routes>
   </BrowserRouter> 
  )
}

export default App;
