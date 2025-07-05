import { useState } from "react";
import { Input } from "../Input";
import { BaseButton } from "../buttons/BaseButton";

export const NotionActionConfigurer = ({ setMetadata }: {
    setMetadata: (params: any) => void;
}) => {
    const [notionSecret, setNotionSecret] = useState("");
    const handleSubmit = () => {
        setMetadata({
            notionSecret
        });
    };

    return <form onSubmit={handleSubmit}>
        <Input label={"Notion Internal Integration Secret"} type={"password"} placeholder="To" onChange={(e) => {
            setNotionSecret(e.target.value)
            e.target.setCustomValidity("")
        }}></Input>
        <div className="pt-4 pb-4 border-black text-xs text-gray-500">To connect your Notion workspace, please enter your Internal Integration Secret.
            This secret is generated from your <a
                href="https://www.notion.so/my-integrations"
                target="_blank"
                rel="noopener noreferrer"
                className="text-blue-600 underline"
            >
                Notion integrations page
            </a>{" "} and allows BlinkFlow to securely interact with your Notion workspace.
            Your secret will be stored securely and only used to access your authorized pages or databases. To know more about Notion's Internal Integration,  
            <a
                href="https://developers.notion.com/docs/create-a-notion-integration"
                target="_blank"
                rel="noopener noreferrer"
                className="text-blue-600 underline"
            >
                click here.
            </a>{" "}
        </div>
        <BaseButton isInactive={!notionSecret.trim()}>Submit</BaseButton>
    </form>
}