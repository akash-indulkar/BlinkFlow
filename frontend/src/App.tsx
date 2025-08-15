import { Routes, Route } from 'react-router-dom';
import { Appbar } from './components/Appbar';
import { Landing } from './Pages/Landing';
import { Signup } from './Pages/Signup';
import { Login } from './Pages/Login';
import { Dashboard } from './Pages/Dashboard';
import { CreateFlow } from './Pages/CreateFlow';
import { EditFlow } from './Pages/EditFlow';
import { Toaster } from 'react-hot-toast';
import { NotFound } from './Pages/NotFound';
import OAuth2Redirect from './Pages/OAuth2Redirect';
import { PrivateRoute } from './auth/PrivateRoute';
import { useDynamicViewport } from './hooks/useDynamicViewport';
import { ForgetPassword } from './Pages/ForgetPassword';
import { ResetPassword } from './Pages/ResetPassword';
import { FlowRunLogs } from './Pages/FlowRunLogs';

function App() {
    useDynamicViewport()
    return (
        <div style={{
            height: "100vh",
        }} className='font-opensans'>
            
                <Appbar />
                <Routes>
                    <Route path={"/"} element={<Landing />} />
                    <Route path={"/signup"} element={<Signup />} />
                    <Route path={"/login"} element={<Login isAuthorized={true} />} />
                    <Route path={"/login/forget"} element={<ForgetPassword/>} />
                    <Route path={"/auth/reset-password"} element={<ResetPassword />} />
                    <Route path={"/login/oauth2/redirect"} element={<OAuth2Redirect />} />
                    <Route path={"/login/redirect"} element={<Login isAuthorized={false} />} />
                    <Route path={"/dashboard"} element={<PrivateRoute><Dashboard/></PrivateRoute>} />
                    <Route path={"/flowrun/logs/:flowID"} element={<PrivateRoute><FlowRunLogs /></PrivateRoute>} />
                    <Route path={"/flow/create"} element={<PrivateRoute><CreateFlow /></PrivateRoute>} />
                    <Route path={"/flow/edit/:flowID"} element={<PrivateRoute><EditFlow /></PrivateRoute>} />
                    <Route path={"/*"} element={<NotFound />} />
                </Routes>
                <Toaster position="top-center" />

        </div>
    );
}

export default App;