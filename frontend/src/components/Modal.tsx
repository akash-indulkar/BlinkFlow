import { useState } from "react";
import { GenericWebhookConfigurer } from "./configurer/GenericWebhookConfigurer";
import { EmailActionConfigurer } from "./configurer/EmailActionConfigurer";
import { NotionActionConfigurer } from "./configurer/NotionActionConfigurer";
import { SlackActionConfigurer } from "./configurer/SlackActionConfigurer";
import { TelegramActionConfigurer } from "./configurer/TelegramActionConfigurer";
import { AsanaTaskActionConfigurer } from "./configurer/AsanaTaskActionConfigurer";
import { TrelloCardActionConfigurer } from "./configurer/TrelloCardActionConfigurer";
import { ClickUpTaskActionConfigurer } from "./configurer/ClickUpTaskActionConfigurer";

export const Modal = ({ index, onSelect, availableItems }: { index: number, onSelect: (props: null | { name: string; id: number; image: string, metadata: any; }) => void, availableItems: { id: number, name: string, image: string; }[] }) => {
    const [step, setStep] = useState(0);
    const [selectedAction, setSelectedAction] = useState<{
        id: number;
        name: string;
        image: string
    }>();
    const [selectedTrigger, setSelectedTrigger] = useState<{
        id: number;
        name: string;
        image: string
    }>();
    const isTrigger = index === 1;

    return <div className="fixed top-0 right-0 left-0 z-50 justify-center items-center w-full md:inset-0 h-[calc(100%-1rem)] max-h-full bg-indigo-50 bg-opacity-70 flex">
        <div className="relative p-4 w-full max-w-2xl max-h-full">
            <div className="relative bg-white rounded-lg shadow ">
                <div className="flex items-center justify-between p-4 md:p-5 border-b rounded-t ">
                    <div className="text-xl">
                        Select {index === 1 ? "Trigger" : "Action"}
                    </div>
                    <button onClick={() => {
                        onSelect(null);
                    }} type="button" className="text-gray-400 bg-transparent hover:bg-indigo-50 hover:text-gray-900 rounded-lg text-sm w-8 h-8 ms-auto inline-flex justify-center items-center" data-modal-hide="default-modal">
                        <svg className="w-3 h-3" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 14 14">
                            <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="m1 1 6 6m0 0 6 6M7 7l6-6M7 7l-6 6" />
                        </svg>
                        <span className="sr-only">Close modal</span>
                    </button>
                </div>
                <div className="p-4 md:p-5 space-y-4">
                    {step === 1 && selectedAction?.name === "Send an email" && <EmailActionConfigurer setMetadata={(metadata) => {
                        onSelect({
                            ...selectedAction,
                            metadata
                        })
                    }} />}
                    {step === 1 && selectedAction?.name === "Add to Notion doc" && <NotionActionConfigurer setMetadata={(metadata) => {
                        onSelect({
                            ...selectedAction,
                            metadata
                        })
                    }} />}
                    {step === 1 && selectedAction?.name === "Send a slack notification" && <SlackActionConfigurer setMetadata={(metadata) => {
                        onSelect({
                            ...selectedAction,
                            metadata
                        })
                    }} />}
                    {step === 1 && selectedAction?.name === "Send a message to Telegram channel" && <TelegramActionConfigurer setMetadata={(metadata) => {
                        onSelect({
                            ...selectedAction,
                            metadata
                        })
                    }} />}
                    {step === 1 && selectedAction?.name === "Add a task to Asana Project" && <AsanaTaskActionConfigurer setMetadata={(metadata) => {
                        onSelect({
                            ...selectedAction,
                            metadata
                        })
                    }} />}
                    {step === 1 && selectedAction?.name === "Create card in Trello list" && <TrelloCardActionConfigurer setMetadata={(metadata) => {
                        onSelect({
                            ...selectedAction,
                            metadata
                        })
                    }} />}
                    {step === 1 && selectedAction?.name === "Create a task in ClickUp List" && <ClickUpTaskActionConfigurer setMetadata={(metadata) => {
                        onSelect({
                            ...selectedAction,
                            metadata
                        })
                    }} />}
                    {step === 0 && <div>{availableItems.map(({ id, name, image }) => {
                        return <div onClick={() => {
                            if (isTrigger) {
                                setStep(s => s+1)
                                setSelectedTrigger({
                                    id,
                                    name,
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
                        }} className="flex border p-4 cursor-pointer hover:bg-indigo-50">
                            <img src={image} className="m-1" width={30} /> <div className="flex flex-col justify-center"> {name} </div>
                        </div>
                    })}</div>}
                    {selectedTrigger && selectedTrigger?.name === "Catch a Webhook" && <GenericWebhookConfigurer setMetadata={(metadata) => {
                        onSelect({
                            ...selectedTrigger,
                            metadata
                        })
                    }} />}
                </div>
            </div>
        </div>

    </div>

}
