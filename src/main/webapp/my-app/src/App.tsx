import './App.css';
import {Route, BrowserRouter, Routes} from 'react-router-dom'
import Table from './Components/Table';
import TestFocusedTable from './Components/TestFocusedTable';
import Home from './Components/Pages/Home';

function App() {
  return (
   <BrowserRouter>
    <Routes>
      <Route path="/" element={<Home />}>
        <Route path="table" element={<Table/>}/>
        <Route path="testFocusedTable" element={<TestFocusedTable/>}/>
      </Route> 
    </Routes>
   </BrowserRouter> 
  )
}

export default App;
