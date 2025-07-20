import { useNavigate } from "react-router-dom"
import { PrimaryButton } from "../components/buttons/PrimaryButton"
import { Input } from "../components/Input"
import { useContext, useState } from "react"
import axios from "axios"
import toast from "react-hot-toast"
import { Loader } from "../components/Loader"
import { AuthContext } from "../auth/AuthContext"
import { GoogleLoginButton } from "../auth/GoogleLoginButton"

export const Signup = () => {
    const { login } = useContext(AuthContext);
    const [name, setName] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [isLoading, setIsLoading] = useState<boolean>(false)
    const router = useNavigate();
    const backendURL = import.meta.env.VITE_BACKEND_URL;

    return <div className="main-content flex justify-center">
        {isLoading && <Loader />}
        <div className="flex mt-[14px] p-10 rounded shadow-lg max-w-4xl bg-white">
            <div className="flex-1 justify-center items-center  h-130 w-130 pt-20">
                <img className="flex justify-center" src="https://res.cloudinary.com/dadualj4l/image/upload/v1752934191/original-9962183004b3c442a836dc3b3e43d49b_lsnezv.jpg" />
            </div>
            <div className="flex-1">
                <div className="px-4">
                    <div className="font-semibold text-xl text-center w-[400px]">
                        Join millions worldwide who automate their work using BlinkFlow.
                    </div>
                    <div className="text-sm text-center w-[400px]">
                        14-day trial of premium features & apps
                    </div>
                </div>
                <div className="shadow-lg border bg-[white] px-4 py-4 m-5 rounded w-[400px]">
                    <GoogleLoginButton/>
                    <div className="my-2 text-center text-gray-500">or</div>

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
                                if (response.data.token) {
                                    login(response.data.token)
                                }
                                toast.success("You account has been created successfully!")
                                router("/dashboard");
                            } catch (error) {
                                setIsLoading(false)
                            }
                        }} size="medium">Get started free</PrimaryButton>
                    </div>
                </div>
            </div>
        </div>
    </div>
}