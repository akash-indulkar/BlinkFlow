
import axios from "axios";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { PrimaryButton } from "../components/buttons/PrimaryButton";
import { FlowCell } from "../components/FlowCell";
import { Input } from "../components/Input";


function useAvailableActionsAndTriggers() {
    const [availableActions, setAvailableActions] = useState([]);
    const [availableTriggers, setAvailableTriggers] = useState([]);

    useEffect(() => {
        axios.get(`${import.meta.env.VITE_BACKEND_URL}/triggers/availabletriggers`)
            .then(res => setAvailableTriggers(res.data))

        axios.get(`${import.meta.env.VITE_BACKEND_URL}/actions/availableactions`)
            .then(res => setAvailableActions(res.data))
    }, [])

    return {
        availableActions,
        availableTriggers
    }
}

export const FlowDemo = () => {
    const router = useNavigate();
    const { availableActions, availableTriggers } = useAvailableActionsAndTriggers();
    const [selectedTrigger, setSelectedTrigger] = useState<{
        id: number;
        name: string;
        image: string;
    }>();
    const [selectedActions, setSelectedActions] = useState<{
        index: number;
        availableActionId: number;
        availableActionName: string;
        availableActionImage: string;
        metadata: any;
    }[]>([]);
    const [selectedModalIndex, setSelectedModalIndex] = useState<null | number>(null);

    return <div>
        <div className="flex justify-end p-4">
            <PrimaryButton onClick={async () => {
                if (!selectedTrigger?.id) {
                    return;
                }

                await axios.post(`${import.meta.env.VITE_BACKEND_URL}/flow/create`, {
                    "availableTriggerID": selectedTrigger.id,
                    "triggerMetadata": {},
                    "flowActions": selectedActions.map(action => ({
                        availableActionID: action.availableActionId,
                        metadata: action.metadata,
                        sortingOrder: action.index
                    }))
                }, {
                    headers: {
                        Authorization: localStorage.getItem("token")
                    }
                })

                router("/dashboard");
            }}>Publish</PrimaryButton>
        </div>
        <div className="w-full min-h-screen flex flex-col justify-start">
            <div className="flex justify-center">
                <FlowCell onClick={() => {
                    setSelectedModalIndex(1);
                }} onDelete={() => {
                    window.alert("hii")
                }} name={selectedTrigger?.name ? selectedTrigger.name : "Select a Trigger"} type="Trigger" image={selectedTrigger?.image ? selectedTrigger.image : ""} index={1} />
            </div>
            <div className="w-full pt-2 pb-2">
                {selectedActions.map((action) => <div className="pt-2 flex justify-center">
                    <FlowCell onClick={() => {
                        setSelectedModalIndex(action.index);
                    }} onDelete={() => {
                        setSelectedActions(prev =>
                            prev
                                .filter(a => a.index !== action.index)
                                .map((a, newIndex) => ({
                                    ...a,
                                    index: newIndex + 2,
                                }))
                        );
                    }} name={action.availableActionName ? action.availableActionName : "Select an Action"} type="Action" image={action.availableActionImage} index={action.index} />
                </div>)}
            </div>
            <div className="flex justify-center">
                <div>
                    <PrimaryButton onClick={() => {
                        setSelectedActions(action => [...action, {
                            index: action.length + 2,
                            availableActionId: 0,
                            availableActionName: "",
                            availableActionImage: "",
                            metadata: {}
                        }])
                    }}>
                        <div className="text-2xl">
                            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={1.5} stroke="currentColor" className="size-6">
                                <path strokeLinecap="round" strokeLinejoin="round" d="M12 9v6m3-3H9m12 0a9 9 0 1 1-18 0 9 9 0 0 1 18 0Z" />
                            </svg>
                        </div>
                    </PrimaryButton>
                </div>
            </div>
        </div>
        {selectedModalIndex && <Modal availableItems={selectedModalIndex === 1 ? availableTriggers : availableActions} onSelect={(props: null | { name: string; id: number; image: string, metadata: any; }) => {
            if (props === null) {
                setSelectedModalIndex(null);
                return;
            }
            if (selectedModalIndex === 1) {
                setSelectedTrigger({
                    id: props.id,
                    name: props.name,
                    image: props.image
                })
            } else {
                setSelectedActions(actions => {
                    let newActions = [...actions];
                    newActions[selectedModalIndex - 2] = {
                        index: selectedModalIndex,
                        availableActionId: props.id,
                        availableActionName: props.name,
                        metadata: props.metadata,
                        availableActionImage: props.image
                    }
                    return newActions
                })
            }
            setSelectedModalIndex(null);
        }} index={selectedModalIndex} />}
        </div>
}

function Modal({ index, onSelect, availableItems }: { index: number, onSelect: (props: null | { name: string; id: number; image: string, metadata: any; }) => void, availableItems: { id: number, name: string, image: string; }[] }) {
    const [step, setStep] = useState(0);
    const [selectedAction, setSelectedAction] = useState<{
        id: number;
        name: string;
        image: string
    }>();
    const isTrigger = index === 1;

    return <div className="fixed top-0 right-0 left-0 z-50 justify-center items-center w-full md:inset-0 h-[calc(100%-1rem)] max-h-full bg-slate-100 bg-opacity-70 flex">
        <div className="relative p-4 w-full max-w-2xl max-h-full">
            <div className="relative bg-white rounded-lg shadow ">
                <div className="flex items-center justify-between p-4 md:p-5 border-b rounded-t ">
                    <div className="text-xl">
                        Select {index === 1 ? "Trigger" : "Action"}
                    </div>
                    <button onClick={() => {
                        onSelect(null);
                    }} type="button" className="text-gray-400 bg-transparent hover:bg-gray-200 hover:text-gray-900 rounded-lg text-sm w-8 h-8 ms-auto inline-flex justify-center items-center" data-modal-hide="default-modal">
                        <svg className="w-3 h-3" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 14 14">
                            <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="m1 1 6 6m0 0 6 6M7 7l6-6M7 7l-6 6" />
                        </svg>
                        <span className="sr-only">Close modal</span>
                    </button>
                </div>
                <div className="p-4 md:p-5 space-y-4">
                    {step === 1 && selectedAction?.name === "Send an email" && <EmailSelector setMetadata={(metadata) => {
                        onSelect({
                            ...selectedAction,
                            metadata
                        })
                    }} />}


                    {step === 0 && <div>{availableItems.map(({ id, name, image }) => {
                        return <div onClick={() => {
                            if (isTrigger) {
                                onSelect({
                                    id,
                                    name,
                                    metadata: {},
                                    image
                                })
                            } else {
                                setStep(s => s + 1);
                                setSelectedAction({
                                    id,
                                    name,
                                    image
                                })
                            }
                        }} className="flex border p-4 cursor-pointer hover:bg-slate-100">
                            <img src={image} width={30} className="rounded-full" /> <div className="flex flex-col justify-center"> {name} </div>
                        </div>
                    })}</div>}
                </div>
            </div>
        </div>
    </div>

}

function EmailSelector({ setMetadata }: {
    setMetadata: (params: any) => void;
}) {
    const [email, setEmail] = useState("");
    const [subject, setSubject] = useState("");
    const [body, setBody] = useState("");


    return <div>
        <Input label={"To"} type={"text"} placeholder="To" onChange={(e) => setEmail(e.target.value)}></Input>
        <Input label={"Subject"} type={"text"} placeholder="Subject" onChange={(e) => setSubject(e.target.value)}></Input>
        {/* <textarea label={"Body"} type={"text"} placeholder="Body" onChange={(e) => setBody(e.target.value)}></textarea> */}

        <div className="pt-2">
            <PrimaryButton onClick={() => {
                setMetadata({
                    email,
                    body
                })
            }}>Submit</PrimaryButton>
        </div>
    </div>
}
