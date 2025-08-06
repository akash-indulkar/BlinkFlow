import axios from "axios";
import { Flow } from "../types/Flow";
import { useNavigate } from "react-router-dom";
import { ConfirmationToast } from "./ConfirmationToast";
import toast from "react-hot-toast";
import { useState } from "react";

const backendURL = import.meta.env.VITE_BACKEND_URL;

export const FlowTable = ({
    flows,
    setFlows
}: {
    flows: Flow[],
    setFlows: React.Dispatch<React.SetStateAction<Flow[]>>
}) => {
    const router = useNavigate();
    const [selectedFlowID, setSelectedFlowID] = useState<number | null>(null);
    const deleteFlow = (flowID: number) => {
        console.log(flowID)
        axios.delete(`${backendURL}/flow/delete/${flowID}`, {
            headers: {
                Authorization: "Bearer " + localStorage.getItem("token")
            }
        })
        toast.success("Flow deleted successfully!");
        setFlows(prevFlows => prevFlows.filter(flow => flow.flowID != flowID))
        setSelectedFlowID(null)
    }

    return <div className="p-4 ">
        {flows.map(flow => <div key={flow.flowID} className="flex border-b border-t px-3 py-3">
            <div className="flex justify-start basis-[20%]">
                <svg className=" m-2" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" height="20" width="20" color="GrayWarm10" name="miscBoltOutlined">
                    <path fill="#2D2E2E" d="M12 2a10 10 0 1 0 0 20 10 10 0 0 0 0-20Zm0 18a8 8 0 1 1 0-16 8 8 0 0 1 0 16Zm1-14-5.87 7H11v5l5.87-7H13V6Z"></path>
                </svg>
                <div className="pt-1 truncate overflow-hidden text-ellipsis max-w-[140px] text-center">{flow.name}</div>
            </div>
            <div className="flex basis-[18%]">
                <img className="w-8 h-8 p-0.5" src={flow.flowTriggerImage}></img>
                {flow.flowActions.length != 0 && <div className="flex">
                    <img className="w-8 h-8 p-0.5" src={flow.flowActions[0]?.flowActionImage}></img>
                    {flow.flowActions.length > 2 ? <span className="text-xl text-center">+{flow.flowActions.length - 1}</span> : <div>{flow.flowActions[1] && <img className="w-8 h-8 p-0.5" src={flow.flowActions[1].flowActionImage}></img>}</div>}
                </div>
                }
            </div>
            <div className="pt-1 flex text-center justify-center basis-[55%]">{`${import.meta.env.VITE_HOOKS_URL}/flowrun/initiate/${flow.userID}/${flow.flowID}`}</div>
            <div className="flex justify-end basis-[18%]">

                <svg onClick={() => {
                    router(`/flow/edit/${flow.flowID}`)
                }}
                    xmlns="http://www.w3.org/2000/svg"
                    width="32"
                    height="32"
                    viewBox="0 0 24 24"
                    fill="none"
                    stroke="currentcolor"
                    strokeWidth="1"
                    strokeLinecap="round"
                    strokeLinejoin="round"
                    className="m-1 p-1 w-9 h-9 hover:cursor-pointer text-center hover:text-blue-500"
                >
                    <path d="M4 20h4l10.5 -10.5a2.828 2.828 0 1 0 -4 -4l-10.5 10.5v4" />
                    <path d="M13.5 6.5l4 4" />
                    <path d="M19 16l-2 3h4l-2 3" />
                </svg>

                <svg onClick={() => {
                    setSelectedFlowID(flow.flowID)
                }}
                    xmlns="http://www.w3.org/2000/svg"
                    width="32"
                    height="32"
                    viewBox="0 0 24 24"
                    fill="none"
                    stroke="currentColor"
                    strokeWidth="1"
                    strokeLinecap="round"
                    strokeLinejoin="round"
                    className="m-1 size-8 pt-1 hover:cursor-pointer hover:text-red-500"
                >
                    <path d="M4 7h16" />
                    <path d="M5 7l1 12a2 2 0 0 0 2 2h8a2 2 0 0 0 2 -2l1 -12" />
                    <path d="M9 7v-3a1 1 0 0 1 1 -1h4a1 1 0 0 1 1 1v3" />
                    <path d="M10 12l4 4m0 -4l-4 4" />
                </svg>

                {selectedFlowID === flow.flowID  && <ConfirmationToast message="Are you sure you want to delete this flow?" onConfirm={() => deleteFlow(flow.flowID)} onCancel={() => { setSelectedFlowID(null) }} />}
            </div>
        </div>)}
    </div>
}