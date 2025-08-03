import { useContext, useRef, useState } from "react";
import { useNavigate } from "react-router-dom";
import { Input } from "../components/Input";
import { PrimaryButton } from "../components/buttons/PrimaryButton";
import axios from "axios";
import toast from "react-hot-toast";
import { AuthContext } from "../auth/AuthContext";
import { GoogleLoginButton } from "../components/buttons/GoogleLoginButton";
import { LoginPageProps } from "../types/LoginPageProps";


export const Login = ({ isAuthorized }: LoginPageProps) => {
    const { login } = useContext(AuthContext);
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [isLoading, setIsLoading] = useState<boolean>(false)
    const router = useNavigate();
    const formRef = useRef<HTMLFormElement>(null);
    const backendURL = import.meta.env.VITE_BACKEND_URL;
    return (
        <form ref={formRef} onSubmit={(e) => e.preventDefault()} noValidate>
            <div className="main-content flex justify-center px-4">
                <div className="flex flex-col md:flex-row mt-16 p-6 rounded shadow-lg max-w-4xl bg-white w-full">
                    <div className="flex-1 px-4 mb-6 md:mb-0 flex justify-center items-center">
                        <img
                            className="hidden md:block w-full max-w-xs md:max-w-sm object-contain"
                            src="https://res.cloudinary.com/dadualj4l/image/upload/v1752934191/original-9962183004b3c442a836dc3b3e43d49b_lsnezv.jpg"
                            alt="Login Illustration" />
                    </div>
                    <div className="flex-1 px-4">
                        <div className="font-semibold text-xl sm:text-2xl text-center">
                            {isAuthorized ? "Welcome back!" : "Sorry, you've logged out!"}
                        </div>
                        <div className="text-sm text-center text-gray-600">
                            Log into your account to access your dashboard.
                        </div>
                        <div className="shadow-lg border bg-white px-4 py-4 mt-6 rounded w-full max-w-sm mx-auto">
                            <GoogleLoginButton />
                            <div className="mt-2 text-center text-gray-500">or</div>
                            <Input
                                onChange={(e) => {setEmail(e.target.value); e.target.setCustomValidity("")}}
                                label="Email"
                                type={"email"}
                                placeholder="Your Email" />
                            <Input
                                onChange={(e) => setPassword(e.target.value)}
                                label="Password"
                                type="password"
                                placeholder="Password" />
                            <button
                                onClick={() => router("/auth/reset-password")}
                                className="mt-2 text-center text-sm text-blue-600 underline"
                            >
                                Forget password? Reset here.
                            </button>
                            <div className="pt-2 mt-2">
                                <PrimaryButton
                                    minWidth="min-w-full"
                                    isLoading={isLoading}
                                    onClick={async () => {
                                        if (!formRef.current?.checkValidity()) {
                                            formRef.current?.reportValidity();
                                            return;
                                        }
                                        try {
                                            setIsLoading(true);
                                            const response = await axios.post(
                                                `${backendURL}/user/login`,
                                                JSON.stringify({ email, password }),
                                                {
                                                    headers: { "Content-type": "application/json" },
                                                }
                                            );
                                            if (response.data.data.token) {
                                                login(response.data.data.token);
                                            }
                                            toast.success("You've logged in successfully!");
                                            setIsLoading(false);
                                            router("/dashboard");
                                        } catch (error) {
                                            setIsLoading(false);
                                            toast.error("Invalid credentials");
                                        }
                                    }}
                                    size="medium">
                                    Login
                                </PrimaryButton>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </form>
    );
}