import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { Appbar } from './components/Appbar';
import { Landing } from './Pages/Landing';
import { Signup } from './Pages/Signup';
import { Login } from './Pages/Login';
import { Dashboard } from './Pages/Dashboard';
import { Flow } from './Pages/Flow';


function App() {

    return (
        <div style={{
            height: "100vh",
        }}>
            <Router>
                <Appbar />
                <Routes>
                    <Route path={"/"} element={<Landing />} />
                    <Route path={"/signup"} element={<Signup/>} />
                    <Route path={"/login"} element={<Login/>} />
                    <Route path={"/dashboard"} element={<Dashboard/>} />
                    <Route path={"/flow/create"} element={<Flow/>} />
                </Routes>
            </Router>

        </div>
    );
}

export default App;