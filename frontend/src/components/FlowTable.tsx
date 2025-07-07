import { Flow } from "../utils/Flow";

export const FlowTable = ({ flows }: { flows: Flow[] }) => {

    return <div className="p-8 w-full">
        {flows.map(flow => <div className=" flex justify-center border-b border-t px-4">
            <div onClick={()=>{
                
            }} className="flex pr-4">{flow.name}</div>
            <img src={flow.flowTriggerImage}></img>
            <img src={flow.flowActionImages[0]}></img>
            {flow.flowActionImages.length > 2 ? <span>+{flow.flowActionImages.length - 1}</span> :  <img src={flow.flowActionImages[1]}></img>}
            <div>{`${import.meta.env.VITE_HOOKS_URL}/hooks/catch/${flow.userID}/${flow.flowID}`}</div>
            <svg onClick={() => {

            }} xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={1.5} stroke="currentColor" className="size-5">
                <path strokeLinecap="round" strokeLinejoin="round" d="m16.862 4.487 1.687-1.688a1.875 1.875 0 1 1 2.652 2.652L6.832 19.82a4.5 4.5 0 0 1-1.897 1.13l-2.685.8.8-2.685a4.5 4.5 0 0 1 1.13-1.897L16.863 4.487Zm0 0L19.5 7.125" />
            </svg>
            <svg onClick={() => {

            }} xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={1.5} stroke="currentColor" className="text-gray-400 text-sm border-black w-5 h-5 absolute top-2 right-2 bg-transparent hover:border-black hover:text-red-500 rounded-lg ">
                <path strokeLinecap="round" strokeLinejoin="round" d="M11.412 15.655 9.75 21.75l3.745-4.012M9.257 13.5H3.75l2.659-2.849m2.048-2.194L14.25 2.25 12 10.5h8.25l-4.707 5.043M8.457 8.457 3 3m5.457 5.457 7.086 7.086m0 0L21 21" />
            </svg>
        </div>)}
    </div>
}