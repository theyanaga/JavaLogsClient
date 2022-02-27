import * as React from "react";
import { Link, Outlet } from "react-router-dom";

// Create home as nav bar
export default function Home() {
    return (
        <div>
            <Link to="/"> Home </Link>
            <Link to="/table"> Table </Link>
            <Link to="/testFocusedTable"> Test Focused Table </Link>
            <Link to="/graph"> Graph </Link>
            <Link to="/score"> Score </Link>
            <Outlet/>
        </div>
    );
}