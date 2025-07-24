import { useContext, useState } from "react";
import { LinkButton } from "./buttons/LinkButton"
import { PrimaryButton } from "./buttons/PrimaryButton";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import { ConfirmationToast } from "./ConfirmationToast";
import { AuthContext } from "../auth/AuthContext";

export const Appbar = () => {
    const router = useNavigate();
    const [isLoading, setIsLoading] = useState<boolean>(true)
    const [name, setName] = useState("")
    const [showModal, setShowModal] = useState<boolean>(false);
    const backendURL = import.meta.env.VITE_BACKEND_URL;
    const { logout } = useContext(AuthContext)
    try {
        axios.get(`${backendURL}/user/me`, {
            headers: {
                "Authorization": "Bearer " + localStorage.getItem("token"),
                "Content-type": "application/json"
            }
        })
            .then(res => {
                setName(res.data.name)
                setIsLoading(false)
            })
    } catch (error) {
    }

    if (isLoading) {
        return <div className="flex shadow-md fixed top-0 left-0 w-full z-50 bg-[#f9faff] justify-between py-3 px-6">
            <div className="flex justify center items-center">
                <img className="w-8 h-8 rounded" src="https://res.cloudinary.com/dadualj4l/image/upload/v1752500433/original-059561fd46a70134d86947adc772082f_wysnzz.jpg"></img>
                <button className="px-1 flex flex-col justify-center text-2xl font-merriweather font-extrabold" onClick={() => {
                    router("/")
                }}>
                    BlinkFlow
                </button>
            </div>
            <div className="flex">
                <div className="pr-4">
                    <LinkButton onClick={() => {
                        router("/login")
                    }}>Login</LinkButton>
                </div>
                <PrimaryButton minWidth="min-w-[100px]" isLoading={false} onClick={() => {
                    router("/signup")
                }}>
                    Signup
                </PrimaryButton>
            </div>
        </div>
    }
    return <div className="flex shadow-md fixed top-0 left-0 w-full z-50 border-b bg-[#f9faff] justify-between py-3 px-6">
        <div className="flex justify center items-center">
            <img className="w-8 h-8 rounded" src="https://res.cloudinary.com/dadualj4l/image/upload/v1752500433/original-059561fd46a70134d86947adc772082f_wysnzz.jpg"></img>
            <button className="px-1 flex flex-col justify-center text-2xl font-merriweather font-extrabold" onClick={() => {
                router("/")
            }}>
                BlinkFlow
            </button>
        </div>
        <div className="text-2xl font-bold font-semibold text-center max-w-xl ">
            {"Hello! " + name}
        </div>
        <div className="flex justify-center">
            <PrimaryButton minWidth="min-w-[120px]" isLoading={false} onClick={() => {
                setShowModal(true)
            }}>
                Logout
            </PrimaryButton>
            {showModal && <ConfirmationToast message="Do you really want to log out?" onConfirm={() => {
                logout()
                window.location.href = "/";
            }} onCancel={() => { setShowModal(false) }} />}
        </div>
    </div>

}