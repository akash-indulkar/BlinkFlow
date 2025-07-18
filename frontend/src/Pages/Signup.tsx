import { useNavigate } from "react-router-dom"
import { PrimaryButton } from "../components/buttons/PrimaryButton"
import { CheckFeature } from "../components/CheckFeature"
import { Input } from "../components/Input"
import { useState } from "react"
import axios from "axios"
import toast from "react-hot-toast"
import { Loader } from "../components/Loader"

export const Signup = () => {
    const [name, setName] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [isLoading, setIsLoading] = useState<boolean>(false)
    const router = useNavigate();
    const backendURL = import.meta.env.VITE_BACKEND_URL;

    return <div className="main-content flex justify-center">
        {isLoading && <Loader/>}
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
            <div className="flex-1 bg-[white]  pt-6 pb-6 mt-12 px-4 border rounded">
                <Input label={"Name"} onChange={e => {
                    setName(e.target.value)
                }} type="text" placeholder="Your name"></Input>
                <Input onChange={e => {
                    setEmail(e.target.value)
                }} label={"Email"} type="email" placeholder="Your Email"></Input>
                <Input onChange={e => {
                    setPassword(e.target.value)
                }} label={"Password"} type="password" placeholder="Password"></Input>

                <div className="pt-4">
                    <PrimaryButton onClick={async () => {
                        
                        try {
                            setIsLoading(true)
                            const response = await axios.post(`${backendURL}/user/signup`, JSON.stringify({
                                email,
                                password,
                                name
                            }), {
                                headers: {
                                    "Content-type": "application/json"
                                }
                            });
                            setIsLoading(false)
                            localStorage.setItem("token", response.data.token);
                            toast.success("You account has been created successfully!")
                            router("/dashboard");
                        } catch (error) {
                            setIsLoading(false)
                        }
                    }} size="big">Get started free</PrimaryButton>
                </div>
            </div>
        </div>
    </div>
}