import { useContext, useState } from "react";
import { useNavigate } from "react-router-dom";
import { Input } from "../components/Input";
import { PrimaryButton } from "../components/buttons/PrimaryButton";
import axios from "axios";
import toast from "react-hot-toast";
import { Loader } from "../components/Loader";
import { AuthContext } from "../auth/AuthContext";
import { GoogleLoginButton } from "../auth/GoogleLoginButton";


export const Login = () => {
    const { login } = useContext(AuthContext);
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [isLoading, setIsLoading] = useState<boolean>(false)
    const router = useNavigate();
    const backendURL = import.meta.env.VITE_BACKEND_URL;

    return <div>
        {isLoading && <Loader />}
        <div className="main-content flex justify-center">
            <div className="flex mt-16 p-6 rounded shadow-lg max-w-4xl bg-white">
                <div className="flex-1  px-4">
                    <img className="flex justify-center" src="https://res.cloudinary.com/dadualj4l/image/upload/v1752934191/original-9962183004b3c442a836dc3b3e43d49b_lsnezv.jpg"></img>
                </div>
                <div className="flex-1">
                    <div className="px-4">
                        <div className="pt-2 font-semibold text-2xl text-center w-[400px]">
                           Welcome back!
                        </div>
                        <div className="text-sm text-center w-[400px]">
                            Log into your account to access your dashboard.
                        </div>
                    </div>
                    <div className="shadow-lg border bg-[white] px-4 py-4 m-6 rounded w-[400px]">
                         <GoogleLoginButton/>
                         <div className="my-2 text-center text-gray-500">or</div>

                        <Input onChange={e => {
                            setEmail(e.target.value)
                        }} label={"Email"} type="email" placeholder="Your Email"></Input>
                        <Input onChange={e => {
                            setPassword(e.target.value);
                        }} label={"Password"} type="password" placeholder="Password"></Input>
                        <div className="pt-2 mt-4">
                            <PrimaryButton onClick={async () => {
                                let res;
                                try {
                                    setIsLoading(true)
                                    res = await axios.post(`${backendURL}/user/login`, JSON.stringify({
                                        email,
                                        password,
                                    }), {
                                        headers: {
                                            "Content-type": "application/json"
                                        }
                                    });
                                    if (res.data.token) {
                                        login(res.data.token)
                                    }
                                    toast.success("You've logged in successfully!")
                                    setIsLoading(false)
                                    router("/dashboard");
                                } catch (error) {
                                    setIsLoading(false)
                                    toast.error("Invalid credentials")
                                }
                            }} size="medium">Login</PrimaryButton>
                            
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div >
}