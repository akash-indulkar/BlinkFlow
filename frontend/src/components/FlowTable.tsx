import axios from "axios";
import { Flow } from "../utils/Flow";
import { useNavigate } from "react-router-dom";
import { toast, ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

const backendURL = import.meta.env.VITE_BACKEND_URL;

export const FlowTable = ({
    flows,
    setFlows
}: {
    flows: Flow[],
    setFlows: React.Dispatch<React.SetStateAction<Flow[]>>
}) => {
    const router = useNavigate();

    const deleteFlow = (flowID: number) => {
        axios.delete(`${backendURL}/flow/delete/${flowID}`, {
            headers: {
                Authorization: "Bearer " + localStorage.getItem("token")
            }
        })
        toast.success("Flow deleted successfully!");
        setFlows(prevFlows => prevFlows.filter(flow => flow.flowID != flowID))
    }

    return <div className="p-4 ">
        {flows.map(flow => <div className="flex border-b border-t px-3 py-3">
            <div className="flex justify-start basis-[20%]">
                <svg className=" m-2" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" height="20" width="20" color="GrayWarm10" name="miscBoltOutlined">
                    <path fill="#2D2E2E" d="M12 2a10 10 0 1 0 0 20 10 10 0 0 0 0-20Zm0 18a8 8 0 1 1 0-16 8 8 0 0 1 0 16Zm1-14-5.87 7H11v5l5.87-7H13V6Z"></path>
                </svg>
                <div className="pt-1 truncate overflow-hidden text-ellipsis max-w-[140px]">{flow.name}</div>
            </div>
            <div className="flex basis-[18%]">
                <img className="w-8 h-8" src={flow.flowTriggerImage}></img>
                {flow.flowActions.length != 0 && <div className="flex">
                    <img className="w-8 h-8" src={flow.flowActions[0]?.flowActionImage}></img>
                    {flow.flowActions.length > 2 ? <span className="text-xl">+{flow.flowActions.length - 1}</span> : <div>{flow.flowActions[1] && <img className="w-8 h-8" src={flow.flowActions[1].flowActionImage}></img>}</div>}
                </div>
                }
            </div>
            <div className="pt-1 flex justify-center basis-[55%]">{`${import.meta.env.VITE_HOOKS_URL}/hooks/catch/${flow.userID}/${flow.flowID}`}</div>
            <div className="flex justify-end basis-[18%]">
                <svg onClick={() => {
                    router(`/flow/edit/${flow.flowID}`)
                }} xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={1.5} stroke="currentColor" className="m-1 pt-1 w-7 h-7 hover:cursor-pointer hover:text-blue-500">
                    <path strokeLinecap="round" strokeLinejoin="round" d="m16.862 4.487 1.687-1.688a1.875 1.875 0 1 1 2.652 2.652L6.832 19.82a4.5 4.5 0 0 1-1.897 1.13l-2.685.8.8-2.685a4.5 4.5 0 0 1 1.13-1.897L16.863 4.487Zm0 0L19.5 7.125" />
                </svg>
                <svg onClick={() => {
                    deleteFlow(flow.flowID)
                    // toast.warn("Are you sure?")
                }} xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={1.5} stroke="currentColor" className="m-1 size-7 hover:cursor-pointer hover:text-red-500">
                    <path strokeLinecap="round" strokeLinejoin="round" d="m14.74 9-.346 9m-4.788 0L9.26 9m9.968-3.21c.342.052.682.107 1.022.166m-1.022-.165L18.16 19.673a2.25 2.25 0 0 1-2.244 2.077H8.084a2.25 2.25 0 0 1-2.244-2.077L4.772 5.79m14.456 0a48.108 48.108 0 0 0-3.478-.397m-12 .562c.34-.059.68-.114 1.022-.165m0 0a48.11 48.11 0 0 1 3.478-.397m7.5 0v-.916c0-1.18-.91-2.164-2.09-2.201a51.964 51.964 0 0 0-3.32 0c-1.18.037-2.09 1.022-2.09 2.201v.916m7.5 0a48.667 48.667 0 0 0-7.5 0" />
                </svg>
            </div>
        </div>)}
        <ToastContainer position="top-right" autoClose={3000} />
    </div>
}