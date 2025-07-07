import { useState } from "react";
import { Input } from "../Input";
import { BaseButton } from "../buttons/BaseButton";

export const NotionActionConfigurer = ({ setMetadata }: {
    setMetadata: (params: any) => void;
}) => {
    const [notionSecret, setNotionSecret] = useState("");
    const [pageID, setPageID] = useState("");
    const handleSubmit = () => {
        setMetadata({
            notionSecret,
            pageID
        });
    };

    return <form onSubmit={handleSubmit}>
        <Input label={"Notion Internal Integration Secret"} type={"password"} placeholder="Your Secret" onChange={(e) => {
            setNotionSecret(e.target.value)
            e.target.setCustomValidity("")
        }}></Input>
        <Input label={"Target Notion Page ID"} type={"text"} placeholder="Page ID" onChange={(e) => {
            setPageID(e.target.value)
            e.target.setCustomValidity("")
        }}></Input>
        <div className="pt-4 pb-4 border-black text-xs text-gray-500">To enable integration with your Notion workspace, please provide your Internal Integration Secret and the Page ID of the target Notion page.
            The Internal Integration Secret is generated from your <a
                href="https://www.notion.so/my-integrations"
                target="_blank"
                rel="noopener noreferrer"
                className="text-blue-600 underline"
            >
                Notion integrations page
            </a>{" "} and allows BlinkFlow to securely interact with your Notion workspace.
            Your secret will be stored securely and only used to access your authorized pages. To know more about Notion's Internal Integration,   {" "}
            <a
                href="https://developers.notion.com/docs/create-a-notion-integration"
                target="_blank"
                rel="noopener noreferrer"
                className="text-blue-600 underline"
            >
                Click here.
            </a>
            {" "} Need help finding your Page ID? <a
                href="https://developers.notion.com/docs/working-with-page-content#creating-a-page-with-content"
                target="_blank"
                rel="noopener noreferrer"
                className="text-blue-600 underline"
            >
                Click here.
            </a> to learn more.
        </div>
        <BaseButton isInactive={!notionSecret.trim() || !pageID.trim()}>Submit</BaseButton>
    </form>
}