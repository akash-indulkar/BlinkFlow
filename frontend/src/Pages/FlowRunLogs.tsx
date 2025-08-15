import axios from "axios";
import { useEffect, useState } from "react";
import { FlowRun } from "../types/FlowRun";
import { useNavigate, useParams } from "react-router-dom";
import { PrimaryButton } from "../components/buttons/PrimaryButton";

export const FlowRunLogs = () => {
    const backendURL = import.meta.env.VITE_BACKEND_URL;
    const [flowName, setFlowName] = useState<string>("");
    const [flowRuns, setFlowRuns] = useState<FlowRun[]>([]);
    const [isLoading, setIsLoading] = useState<boolean>(true)
    const { flowID } = useParams<{ flowID: string }>();
    const router = useNavigate();

    useEffect(() => {
        axios.get(`${backendURL}/flowrun/logs/${flowID}`, {
            headers: {
                "Authorization": "Bearer " + localStorage.getItem("token"),
                "Content-type": "application/json"
            }
        })
            .then(response => {
                setFlowName(response.data.data[0].flowName);
                setFlowRuns(response.data.data);
                setIsLoading(false)
            })
    }, []);
    return (
        <div className="flex-col justify-center py-20 px-8 sm:py-20 sm:px-4 bg-gray-50 min-h-screen">
            <div className="max-w-5xl mx-auto overflow-x-auto flex justify-between mb-6 text-center">
                <span className="text-xl sm:text-2xl font-bold text-gray-600">{flowName}</span>

                <PrimaryButton minWidth='min-w-[170px]' isLoading={false} onClick={() => {
                    router("/dashboard")
                }}>Go to Dashboard</PrimaryButton>
            </div>
            <div className="max-w-5xl mx-auto overflow-x-auto bg-white shadow-lg rounded-lg">
                <table className="w-full border-collapse">
                    <thead>
                        <tr className="bg-gray-100 text-gray-600 uppercase text-xs sm:text-sm leading-normal">
                            <th className="py-3 px-4 sm:px-6 text-left">Triggered At</th>
                            <th className="flex justify-end py-3 px-4 sm:px-8 text-left">Status</th>
                        </tr>
                    </thead>
                    {isLoading ? (
                        <tbody>
                            <tr>
                                <td colSpan={2} className="py-10 text-center">
                                    <div className="w-10 h-10 border-4 border-blue-500 border-t-transparent rounded-full animate-spin mx-auto"></div>
                                </td>
                            </tr>
                        </tbody>
                    ) : (
                        <tbody className="text-gray-700 text-xs sm:text-sm">
                            {flowRuns.length > 0 ? (
                                flowRuns.map((flowRun, index) => (
                                    <tr
                                        key={index}
                                        className="border-b border-gray-200 hover:bg-gray-50"
                                    >
                                        <td className="py-3 px-4 sm:px-6">
                                            {new Date(flowRun.triggeredAt).toLocaleString()}
                                        </td>
                                        <td className="flex justify-end py-3 px-4 sm:px-6">
                                            <span
                                                className={`px-2 py-1 rounded-lg min-w-[70px] text-center item-center text-xs font-medium sm:px-3 ${flowRun.status === "SUCCESS"
                                                    ? "bg-green-100 text-green-700"
                                                    : flowRun.status === "FAILED"
                                                        ? "bg-red-100 text-red-700"
                                                        : "bg-yellow-100 text-yellow-700"
                                                    }`}
                                            >
                                                {flowRun.status}
                                            </span>
                                        </td>
                                    </tr>
                                ))
                            ) : (
                                <tr>
                                    <td
                                        colSpan={2}
                                        className="text-center py-4 text-gray-500 italic"
                                    >
                                        No runs found
                                    </td>
                                </tr>
                            )}
                        </tbody>
                    )}
                </table>
            </div>
        </div>
    );
};

