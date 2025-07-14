import { useState } from "react";
import { LinkButton } from "./buttons/LinkButton"
import { PrimaryButton } from "./buttons/PrimaryButton";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import { ConfirmationToast } from "./ConfirmationToast";

export const Appbar = () => {
    const router = useNavigate();
    const [loading, setLoading] = useState(true);
    const [name, setName] = useState("")
    const [showModal, setShowModal] = useState<boolean>(false);
    const backendURL = import.meta.env.VITE_BACKEND_URL;
    const logoutUser = () => {
        localStorage.setItem("token", "")
        window.location.href = "/";
    }
    try {
        axios.get(`${backendURL}/user/me`, {
            headers: {
                "Authorization": "Bearer " + localStorage.getItem("token"),
                "Content-type": "application/json"
            }
        })
            .then(res => {
                setName(res.data.name)
                setLoading(false)
            })
    } catch (error) {
        console.log("hii")
    }
    if (loading) {
        return <div className="flex shadow-md fixed top-0 left-0 w-full z-50 bg-[#f9faff] justify-between py-3 px-6">
            <div className="flex justify center items-center">
                <img className="w-8 h-8 rounded" src="https://res.cloudinary.com/dadualj4l/image/upload/v1752500433/original-059561fd46a70134d86947adc772082f_wysnzz.jpg"></img>
                <button className="px-1 flex flex-col justify-center text-2xl font-extrabold" onClick={() => {
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
                <PrimaryButton onClick={() => {
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
            <button className="px-1 flex flex-col justify-center text-2xl font-extrabold" onClick={() => {
                router("/")
            }}>
                BlinkFlow
            </button>
        </div>
        <div className="text-2xl font-bold font-semibold text-center max-w-xl ">
            {"Hello! " + name}
        </div>
        <div className="flex justify-center">
            <PrimaryButton onClick={() => {
                setShowModal(true)
            }}>
                Logout
            </PrimaryButton>
            {showModal && <ConfirmationToast message="Do you really want to log out?" onConfirm={() => logoutUser()} onCancel={() => { setShowModal(false) }} />}
        </div>
    </div>

}