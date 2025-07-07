import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { Appbar } from './components/Appbar';
import { Landing } from './Pages/Landing';
import { Signup } from './Pages/Signup';
import { Login } from './Pages/Login';
import { Dashboard } from './Pages/Dashboard';
import { CreateFlow } from './Pages/CreateFlow';
import { EditFlow } from './Pages/EditFlow';


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
                    <Route path={"/flow/create"} element={<CreateFlow/>} />
                    <Route path={"/flow/edit/:flowID"} element={<EditFlow/>} />
                </Routes>
            </Router>

        </div>
    );
}

export default App;