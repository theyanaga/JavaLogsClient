import React from 'react';
import { 
  BrowserRouter,
  Routes,
  Route
 } from 'react-router-dom';
import logo from './logo.svg';
import BasicTable from './Components/Table';
import Home from './Components/Pages/Home'
import './App.css';
import Table from './Components/Table';

function App() {
  return (
   <BrowserRouter>
    <Routes>
      <Route path="/" element={<Home />}>
        <Route path="table" element={<Table/>}/>
      </Route> 
    </Routes>
   </BrowserRouter> 

  )
  // return (
  //     BasicTable()
  //     )
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
