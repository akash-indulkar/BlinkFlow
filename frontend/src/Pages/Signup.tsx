import { useNavigate } from "react-router-dom"
import { PrimaryButton } from "../components/buttons/PrimaryButton"
import { Input } from "../components/Input"
import { useContext, useState } from "react"
import axios from "axios"
import toast from "react-hot-toast"
import { Loader } from "../components/Loader"
import { AuthContext } from "../auth/AuthContext"
import { GoogleLoginButton } from "../components/buttons/GoogleLoginButton"

export const Signup = () => {
    const { login } = useContext(AuthContext);
    const [name, setName] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [isLoading, setIsLoading] = useState<boolean>(false)
    const router = useNavigate();
    const backendURL = import.meta.env.VITE_BACKEND_URL;

    return (
        <div className="main-content flex justify-center px-4">
            {isLoading && <Loader />}
            <div className="flex flex-col md:flex-row mt-2 p-6 md:p-10 rounded shadow-lg max-w-4xl w-full bg-white">
                <div className="flex-1 flex justify-center items-center py-6">
                    <img
                        className="hidden md:block w-full max-w-xs md:max-w-sm object-contain"
                        src="https://res.cloudinary.com/dadualj4l/image/upload/v1752934191/original-9962183004b3c442a836dc3b3e43d49b_lsnezv.jpg"
                        alt="Sign up illustration"/>
                </div>
                <div className="flex-1 px-4">
                    <div className="font-semibold text-lg sm:text-xl text-center mb-2">
                        Join millions worldwide who automate their work using BlinkFlow.
                    </div>
                    <div className="text-sm text-center text-gray-600 mb-4">
                        14-day trial of premium features & apps
                    </div>
                    <div className="shadow-lg border bg-white px-4 py-6 rounded w-full max-w-sm mx-auto">
                        <GoogleLoginButton />
                        <div className="my-2 text-center text-gray-500">or</div>
                        <Input
                            label={"Name"}
                            onChange={(e) => setName(e.target.value)}
                            type="text"
                            placeholder="Your name"/>
                        <Input
                            onChange={(e) => setEmail(e.target.value)}
                            label={"Email"}
                            type="email"
                            placeholder="Your Email"/>
                        <Input
                            onChange={(e) => setPassword(e.target.value)}
                            label={"Password"}
                            type="password"
                            placeholder="Password"/>
                        <div className="pt-4">
                            <PrimaryButton
                                minWidth="min-w-full"
                                isLoading={isLoading}
                                onClick={async () => {
                                    try {
                                        setIsLoading(true);
                                        const response = await axios.post(
                                            `${backendURL}/user/signup`,
                                            JSON.stringify({ email, password, name }),
                                            { headers: { "Content-type": "application/json" } }
                                        );
                                        setIsLoading(false);
                                        if (response.data.token) {
                                            login(response.data.token);
                                        }
                                        toast.success("Your account has been created successfully!");
                                        router("/dashboard");
                                    } catch (error) {
                                        setIsLoading(false);
                                    }
                                }}
                                size="medium">
                                Get started for free
                            </PrimaryButton>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}
