
import axios from "axios";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { Flow } from "../types/Flow";
import { FlowTable } from "../components/FlowTable";
import { PrimaryButton } from "../components/buttons/PrimaryButton";
import { Loader } from "../components/Loader";

function useFlows() {
    const [flows, setFlows] = useState<Flow[]>([]);
    const backendURL = import.meta.env.VITE_BACKEND_URL;
    const [isLoading, setIsLoading] = useState<boolean>(true)

    useEffect(() => {
        axios.get(`${backendURL}/flow/`, {
            headers: {
                "Authorization": "Bearer " + localStorage.getItem("token"),
                "Content-type": "application/json"
            }
        })
            .then(res => {
                setFlows(res.data);
                setIsLoading(false)
            })
    }, []);

    return {
        isLoading, flows, setFlows
    }
}

export const Dashboard = () => {
    const { isLoading, flows, setFlows } = useFlows();
    const router = useNavigate();

    if (!isLoading && flows.length === 0) {
        return <div className="pt-10 main-content flex-col justify-center ">
            {isLoading && <Loader />}
            <div className="flex justify-center pt-8">
                <img src="https://res.cloudinary.com/dadualj4l/image/upload/v1752380954/BlinkFlowCloudStore/not_found_dog-98fd5bff8642dd0089c45b3a82fe5d56_a5wiaw.svg"></img>
            </div>
            <div className="flex justify-center pt-8">
                <span className="text-2xl px-2">You don't have any flow, Let's create one</span>
                <PrimaryButton minWidth="min-w-[98px]" isLoading={false} onClick={() => {
                    router("/flow/create");
                }}>Create</PrimaryButton>
            </div>
        </div>
    }

    return <div className="main-content flex-col justify-center ">
        {isLoading && <Loader />}
        <div className="flex justify-center pt-8 px-4">
            <div className="max-w-screen-lg	 w-full">
                <div className="flex justify-between ">
                    <div className="text-2xl font-bold">
                        My Flows
                    </div>
                    <PrimaryButton minWidth="min-w-[98px]" isLoading={false} onClick={() => {
                        router("/flow/create");
                    }}>Create</PrimaryButton>
                </div>
            </div>
        </div>

        <div className="p-4 flex-col">
            <div className="flex justify-center ">
                <div className="max-w-screen-lg	w-full border border-gray-300 rounded-md">
                    <div className="flex justify-between px-6 py-4 w-full font-medium  max-w-screen-lg w-full">
                        <div className="flex items-center basis-[25%]">
                            <svg className="basis-[20%]" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" height="20" width="20" color="GrayWarm10" name="miscBoltOutlined">
                                <path fill="#2D2E2E" d="M12 2a10 10 0 1 0 0 20 10 10 0 0 0 0-20Zm0 18a8 8 0 1 1 0-16 8 8 0 0 1 0 16Zm1-14-5.87 7H11v5l5.87-7H13V6Z"></path>
                            </svg>
                            <span className="basis-[110%] px-1">Name</span>
                            <div className="basis-[20%]">Apps</div>
                        </div>
                        <div className="basis-[66%]">
                            <div className="flex justify-center">Webhook URL</div>
                        </div>
                        <div className="basis-[7.3%]">
                            <div className="">Manage</div>
                        </div>
                    </div>
                </div>
            </div>
            {isLoading ?
                <div className="flex justify-center">
                    <div className="divide-y divide-gray-300 max-w-screen-lg w-full border border-gray-300 rounded-md overflow-hidden">
                        <div role="status" className="divide-y divide-gray-300 max-w-screen-lg w-full divide-gray-200 rounded shadow animate-pulse dark:divide-gray-700 p-4 dark:border-gray-700">
                            <div className=" flex w-full justify-between">
                                <div className="flex gap-x-10 basis-[30%]">
                                    <div className="basis-[150%] h-6 bg-gray-300 dark:bg-gray-600 mb-2.5"></div>
                                    <div className="basis-[150%] h-6 bg-gray-300 dark:bg-gray-600 mb-2.5"></div>
                                </div>
                                <div className="basis-[50%] h-6 bg-gray-300  dark:bg-gray-600 mb-2.5"></div>
                                <div className="basis-[8%] h-6 bg-gray-300  dark:bg-gray-600 mb-2.5"></div>
                            </div>
                            <div className="flex justify-between pt-4">
                                <div className="flex gap-x-10 basis-[30%]">
                                    <div className="basis-[100%] h-6 bg-gray-300  dark:bg-gray-600 mb-2.5"></div>
                                    <div className="basis-[100%] h-6 bg-gray-300  dark:bg-gray-600 mb-2.5"></div>
                                </div>
                                <div className="basis-[50%] h-6 bg-gray-300  dark:bg-gray-600 mb-2.5"></div>
                                <div className="basis-[8%] h-6 bg-gray-300  dark:bg-gray-600 mb-2.5"></div>
                            </div>
                            <div className="flex justify-between pt-4">
                                <div className="flex gap-x-10 basis-[30%]">
                                    <div className="basis-[100%] h-6 bg-gray-300  dark:bg-gray-600 mb-2.5"></div>
                                    <div className="basis-[100%] h-6 bg-gray-300  dark:bg-gray-600 mb-2.5"></div>
                                </div>
                                <div className="basis-[50%] h-6 bg-gray-300  dark:bg-gray-600 mb-2.5"></div>
                                <div className="basis-[8%] h-6 bg-gray-300  dark:bg-gray-600 mb-2.5"></div>
                            </div>
                            <div className="flex justify-between pt-4">
                                <div className="flex gap-x-10 basis-[30%]">
                                    <div className="basis-[100%] h-6 bg-gray-300  dark:bg-gray-600 mb-2.5"></div>
                                    <div className="basis-[100%] h-6 bg-gray-300  dark:bg-gray-600 mb-2.5"></div>
                                </div>
                                <div className="basis-[50%] h-6 bg-gray-300  dark:bg-gray-600 mb-2.5"></div>
                                <div className="basis-[8%] h-6 bg-gray-300  dark:bg-gray-600 mb-2.5"></div>
                            </div>
                            <div className="flex justify-between pt-4">
                                <div className="flex gap-x-10 basis-[30%]">
                                    <div className="basis-[100%] h-6 bg-gray-300  dark:bg-gray-600 mb-2.5"></div>
                                    <div className="basis-[100%] h-6 bg-gray-300  dark:bg-gray-600 mb-2.5"></div>
                                </div>
                                <div className="basis-[50%] h-6 bg-gray-300  dark:bg-gray-600 mb-2.5"></div>
                                <div className="basis-[8%] h-6 bg-gray-300  dark:bg-gray-600 mb-2.5"></div>
                            </div>
                            <div className="flex justify-between pt-4">
                                <div className="flex gap-x-10 basis-[30%]">
                                    <div className="basis-[100%] h-6 bg-gray-300  dark:bg-gray-600 mb-2.5"></div>
                                    <div className="basis-[100%] h-6 bg-gray-300  dark:bg-gray-600 mb-2.5"></div>
                                </div>
                                <div className="basis-[50%] h-6 bg-gray-300  dark:bg-gray-600 mb-2.5"></div>
                                <div className="basis-[8%] h-6 bg-gray-300  dark:bg-gray-600 mb-2.5"></div>
                            </div>
                        </div>
                    </div>
                </div>
                : <div className="flex justify-center">
                    <div className="max-w-screen-lg w-full border border-gray-300 rounded-md overflow-hidden">
                        <FlowTable flows={flows} setFlows={setFlows} />
                    </div>
                </div>
            }
        </div>
    </div>
}

