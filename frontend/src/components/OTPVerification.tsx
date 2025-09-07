import { useContext, useEffect, useRef, useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import toast from "react-hot-toast";
import { AuthContext } from "../auth/AuthContext";

export const OTPVerification = ({ email, name, password, type, onClose }: { email: string, name: string, password: string, type: string, onClose: () => void }) => {
    const [otp, setOtp] = useState<string[]>(Array(6).fill(""));
    const [timer, setTimer] = useState<number>(60);
    const [timerKey, setTimerKey] = useState(0);
    const [resendOTPAttempts, setResendOTPAttempts] = useState<number>(0);
    const [invalidOTPAttempts, setInvalidOTPAttempts] = useState<number>(0);
    const inputRefs = useRef<(HTMLInputElement | null)[]>([]);
    const router = useNavigate();
    const backendURL = import.meta.env.VITE_BACKEND_URL;
    const { login } = useContext(AuthContext);

    useEffect(() => {
        setTimer(60);
        const countdown = setInterval(() => {
            setTimer((prev) => {
                if (prev <= 1) {
                    clearInterval(countdown);
                    return 0;
                }
                return prev - 1;
            });
        }, 1000);
        return () => clearInterval(countdown);
    }, [timerKey]);


    const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>, index: number) => {
        const value = e.target.value;
        if (!/^[0-9]?$/.test(value)) return;

        const updatedOtp = [...otp];
        updatedOtp[index] = value;
        setOtp(updatedOtp);

        if (value && index < 5) inputRefs.current[index + 1]?.focus();
    };

    const handleKeyDown = (e: React.KeyboardEvent<HTMLInputElement>, index: number) => {
        if (e.key === "Backspace") {
            if (otp[index]) {
                const updatedOtp = [...otp];
                updatedOtp[index] = "";
                setOtp(updatedOtp);
            } else if (index > 0) {
                inputRefs.current[index - 1]?.focus();
            }
        }
    };

    const handlePaste = (e: React.ClipboardEvent<HTMLInputElement>) => {
        const text = e.clipboardData.getData("text");
        if (/^\d{6}$/.test(text)) {
            const digits = text.split("");
            setOtp(digits);
            inputRefs.current[5]?.focus();
        }
    };

    const handleResend = async () => {
        try {
            if (type === "reset") {
                await axios.post(
                    `${backendURL}/user/login/forget?emailID=${email}`,
                    {},
                    { headers: { "Content-type": "application/json" } }
                );
            } else if (type === "signup") {
                await axios.post(
                    `${backendURL}/user/signup`,
                    JSON.stringify({ email, password, name }),
                    { headers: { "Content-type": "application/json" } }
                );
            }
            toast.success("OTP resent successfully!");
        } catch (error) {
            const nextAttempts = resendOTPAttempts + 1;
            setResendOTPAttempts(nextAttempts);
            if (nextAttempts >= 3) {
                toast.error("You have exceeded the resend limit. Please try again later.");
                router("/");
                return;
            } else {
                toast("OTP Resend attempts left: " + (3 - nextAttempts));
            }
        } finally {
            setOtp(Array(6).fill(""));
            setTimerKey((prev) => prev + 1); 
        }
    };


    const handleSubmit = async () => {
        const otpCode = otp.join("");
        if (type === "reset") {
            try {
                await axios.post(
                    `${backendURL}/user/login/reset`,
                    JSON.stringify({ emailID: email, password, otp: otpCode }),
                    {
                        headers: { "Content-type": "application/json" },
                    }
                );
                toast.success("You're password changed successfully!");
                onClose();
                router("/login");
            } catch (error) {
                const nextAttempts = invalidOTPAttempts + 1;
                setInvalidOTPAttempts(nextAttempts);
                if (nextAttempts >= 3) {
                    toast.error("Multiple invalid attempts detected. Try again after some time.");
                    router("/");
                } else {
                    toast("Invalid OTP. Attempts left: " + (3 - nextAttempts));
                }
            }
        } else if (type === "signup") {
            try {
                const response = await axios.post(
                    `${backendURL}/user/signup/verify`,
                    JSON.stringify({ emailID: email, otp: otpCode }),
                    {
                        headers: { "Content-type": "application/json" },
                    }
                );
                if (response.data.data.token) {
                    login(response.data.data.token);
                }
                toast.success("Your account has been created successfully!");
                onClose();
                router("/dashboard");
            } catch (error) {
                const nextAttempts = invalidOTPAttempts + 1;
                setInvalidOTPAttempts(nextAttempts);
                if (nextAttempts >= 3) {
                    toast.error("Multiple invalid attempts detected. Try again after some time.");
                    router("/");
                } else {
                    toast("Invalid OTP. Attempts left: " + (3 - nextAttempts));
                }
            }
        }
    };

    const isSubmitDisabled = (otp.some((digit) => digit === "") || timer === 0);

    return (
        <div className="min-h-screen flex items-center justify-center bg-gray-100 p-4 fixed inset-0 bg-black bg-opacity-40 backdrop-blur-sm flex items-center justify-center z-40">
            <div className="bg-white rounded-xl p-6 shadow-lg w-full max-w-md text-center flex flex-col items-center relative">
                <button
                    type="button"
                    onClick={onClose}
                    className="text-gray-400 absolute top-2 right-2 hover:bg-gray-200 hover:text-gray-900 rounded-lg text-sm p-1.5">
                    <svg aria-hidden="true" className="w-5 h-5" fill="currentColor" viewBox="0 0 20 20">
                        <path fillRule="evenodd" d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 
            1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 
            11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 
            10 4.293 5.707a1 1 0 010-1.414z" clipRule="evenodd" />
                    </svg>
                    <span className="sr-only">Close modal</span>
                </button>
                <h2 className="text-xl font-bold mb-2">Enter the 6-digit OTP</h2>
                <div className="mb-4 text-sm text-gray-600">We've sent you a verification OTP on your email</div>
                <div className="flex justify-center gap-2 mb-4">
                    {otp.map((digit, index) => (
                        <input
                            key={index}
                            type="text"
                            maxLength={1}
                            value={digit}
                            onChange={(e) => handleInputChange(e, index)}
                            onKeyDown={(e) => handleKeyDown(e, index)}
                            onPaste={handlePaste}
                            ref={(el) => (inputRefs.current[index] = el)}
                            className="w-12 h-12 border rounded text-center text-xl font-semibold focus:outline-none"
                        />
                    ))}
                </div>
                <div className="mb-2 text-sm text-gray-600">Time left: {timer}s</div>
                <button
                    onClick={handleResend}
                    disabled={timer > 0}
                    className={`mb-4 text-sm ${timer === 0 ? "text-blue-600" : "text-gray-400 cursor-not-allowed"}`}
                >
                    Resend OTP
                </button>
                <button
                    onClick={handleSubmit}
                    disabled={isSubmitDisabled}
                    className={`w-full py-2 rounded text-white font-semibold ${isSubmitDisabled ? "cursor-not-allowed bg-slate-300" : "bg-amber-700"
                        }`}
                >
                    Submit
                </button>
                <button
                    onClick={() => router("/")}
                    className="mt-4 text-sm text-blue-600 underline"
                >
                    Go to home page
                </button>
            </div>
        </div>
    );
};
