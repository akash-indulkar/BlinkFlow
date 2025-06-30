import { Flow } from "../utils/Flow";

export const FlowTable = ({ flows }: { flows : Flow[] }) => {
    console.log(flows)

    return <div className="p-8 w-full">
        <div className="flex justify-between px-4">
            <div className="flex justify-between" >
                <div className="pr-48">Name</div>
                <div >ID</div>
            </div>
            <div >Webhook URL</div>
        </div>
        {flows.map(flow => <div className=" flex justify-center border-b border-t px-4">
            <div className="flex pr-4"></div>
            <div className="flex pr-4">{flow.flowID}</div>
            <div >{`${import.meta.env.VITE_HOOKS_URL}/hooks/catch/${flow.userID}/${flow.flowID}`}</div>
        </div>)}
    </div>
}