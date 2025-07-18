import { useNavigate } from "react-router-dom";
import { Feature } from "./Feature"
import { PrimaryButton } from "../buttons/PrimaryButton"
import { SecondaryButton } from "../buttons/SecondaryButton"
import { useEffect, useState } from "react";
import axios from "axios";
import { Loader } from "../Loader";

export const Hero = () => {
    const backendURL = import.meta.env.VITE_BACKEND_URL;
    const [isLoading, setIsLoading] = useState<boolean>(true)
    const [isLoggedIn, setIsLoggedIn] = useState<boolean>(false)
    const router = useNavigate();
    useEffect(() => {
        try {
            axios.get(`${backendURL}/user/me`, {
                headers: {
                    "Authorization": "Bearer " + localStorage.getItem("token"),
                    "Content-type": "application/json"
                }
            })
                .then(res => {
                    setIsLoggedIn(true)
                    setIsLoading(false)
                })
                .catch(e => {
                    console.log(e)
                    setIsLoading(false)
                })
        } catch (error) {
            console.log(error)
        }
    })
    return <div>
        {isLoading && <Loader />}
        <div className="flex justify-center">
            <div className="text-5xl font-bold font-semibold text-center pt-8 max-w-xl">
                Automate as fast as you can type !
            </div>
        </div>
        <div className="flex justify-center pb-8">
            <div className="text-xl font-bold font-normal text-center pt-8 max-w-2xl">
                AI gives you automation superpowers, and BlinkFlow puts them to work. Pairing AI and BlinkFlow helps you turn ideas into workflows and bots that work for you.
            </div>
        </div>
        <div className="flex justify-center pt-4 pb-8">
            {isLoggedIn && <div className="flex">
                <PrimaryButton onClick={() => {
                    setIsLoading(true)
                    router("/flow/create")
                    setIsLoading(false)
                }} size="big"> Let's create a Flow! </PrimaryButton>
                <div className="pl-4 min-w-44">
                    <SecondaryButton onClick={() => {
                        setIsLoading(true)
                        router("/dashboard")
                        setIsLoading(false)
                    }} size="big">Go to DashBoard</SecondaryButton>
                </div>
            </div>}
            {!isLoggedIn && <div className="flex">
                <PrimaryButton onClick={() => {
                    setIsLoading(true)
                    router("/signup")
                    setIsLoading(false)
                }} size="big"> Let's Get Started for free ! </PrimaryButton>
                <div className="pl-4 min-w-44">
                    <SecondaryButton onClick={() => {
                        setIsLoading(true)
                        router("/login")
                        setIsLoading(false)
                    }} size="big">Have an Account? Login</SecondaryButton>
                </div>
            </div>}
        </div>

        <div className="flex justify-center pt-4 pb-8">
            <Feature title={"Free Forever"} subtitle={"for core features"} />
            <Feature title={"More apps"} subtitle={"than any other platforms"} />
            <Feature title={"Cutting Edge"} subtitle={"AI Features"} />
        </div>
    </div>
}