import { useState, useRef } from "react";
import toast from "react-hot-toast";
import axios from "axios";
import { Input } from "../components/Input";
import { PrimaryButton } from "../components/buttons/PrimaryButton";
import { OTPVerification } from "../components/OTPVerification";

export const ResetPassword = () => {
    const [email, setEmail] = useState("");
    const [newPassword, setNewPassword] = useState("");
    const [confirmPassword, setConfirmPassword] = useState("");
    const [isLoading, setIsLoading] = useState(false);
    const [showOTPModal, setShowOTPModal] = useState<boolean>(false);
    const formRef = useRef<HTMLFormElement>(null);
    const backendURL = import.meta.env.VITE_BACKEND_URL;

    const handleSubmit = async () => {

    };

    return (
        <form
            ref={formRef}
            onSubmit={(e) => {
                e.preventDefault()
                handleSubmit()
            }} noValidate
            className="flex justify-center main-content items-center min-h-screen bg-#f9faff px-4"
        >
            <div className="w-full max-w-md bg-white p-6 rounded shadow-md">
                <h2 className="text-2xl font-semibold text-center mb-4">Reset Password</h2>

                <Input
                    label="Email"
                    type="email"
                    onChange={(e) => {
                        setEmail(e.target.value)
                        e.target.setCustomValidity("")
                    }}
                    placeholder="Enter your email"
                />

                <Input
                    label="New Password"
                    type="password"
                    onChange={(e) => setNewPassword(e.target.value)}
                    placeholder="New password"
                />

                <Input
                    label="Confirm Password"
                    type="password"
                    onChange={(e) => setConfirmPassword(e.target.value)}
                    placeholder="Confirm new password"
                />

                <div className="mt-4">
                    <PrimaryButton
                        onClick={ async () => {
                            if (!formRef.current?.checkValidity()) {
                                formRef.current?.reportValidity();
                                return;
                            }
                            if (newPassword !== confirmPassword) {
                                toast.error("Passwords do not match.");
                                return;
                            }
                            try {
                                setIsLoading(true);
                                await axios.post(
                                    `${backendURL}/user/login/forget?emailID=${email}`,
                                    {
                                        headers: { "Content-type": "application/json" },
                                    }
                                );
                                setShowOTPModal(true)
                                setIsLoading(false);
                                toast.success("We've sent you a verification OTP on your email!");
                            } catch (err) {
                                setIsLoading(false);
                                toast.error("Failed to send OTP. Please try again after some time.");
                            }
                        }}
                        minWidth="min-w-full"
                        size="medium"
                        isLoading={isLoading}
                    >
                        Reset Password
                    </PrimaryButton>
                </div>
            </div>
            {showOTPModal && (
                <OTPVerification
                    email={email}
                    name=""
                    password={newPassword}
                    type="reset"
                    onClose={() => setShowOTPModal(false)}
                />
            )}
        </form>
    );
};
