import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { CheckFeature } from "../components/CheckFeature";
import { Input } from "../components/Input";
import { PrimaryButton } from "../components/buttons/PrimaryButton";
import axios from "axios";

export const Login = () => {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const router = useNavigate();
    
    return <div> 

        <div className="flex justify-center">
            <div className="flex pt-8 max-w-4xl">
                <div className="flex-1 pt-20 px-4">
                    <div className="font-semibold text-3xl pb-4">
                    Join millions worldwide who automate their work using BlinkFlow.
                    </div>
                    <div className="pb-6 pt-4">
                        <CheckFeature label={"Easy setup, no coding required"} />
                    </div>
                    <div className="pb-6">
                        <CheckFeature label={"Free forever for core features"} />
                    </div>
                    <CheckFeature label={"14-day trial of premium features & apps"} />

                </div>
                <div className="flex-1 pt-6 pb-6 mt-12 px-4 border rounded">
                    <Input onChange={e => {
                        setEmail(e.target.value)
                    }} label={"Email"} type="text" placeholder="Your Email"></Input>
                    <Input onChange={e => {
                        setPassword(e.target.value);
                    }} label={"Password"} type="password" placeholder="Password"></Input>
                    <div className="pt-4">
                        <PrimaryButton onClick={async () => {
                            const res = await axios.post(`${import.meta.env.VITE_BACKEND_URL}/user/login`, JSON.stringify({
                                email,
                                password,
                            }), {
                                headers: {
                                    "Content-type": "application/json"
                                }
                            });
                            localStorage.setItem("token", res.data.token);
                            router("/dashboard");
                        }} size="big">Login</PrimaryButton>
                    </div>
                </div>
            </div>
        </div>
    </div>
}