import React from 'react';
import logo from './logo.svg';
import BasicTable from './Components/Table';
import './App.css';
<<<<<<< Updated upstream

function App() {
  return (
      BasicTable()
      )
=======
import Table from './Components/Table';
import TestFocusedTable from './Components/TestFocusedTable';

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
  // return (
  //     BasicTable()
  //     )
>>>>>>> Stashed changes
      /* <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        <p>
          Edit <code>src/App.tsx</code> and save to reload.
        </p>
        <a
          className="App-link"
          href="https://reactjs.org"
          target="_blank"
          rel="noopener noreferrer"
        >
          Learn React
        </a>
      </header> */
}

export default App;
