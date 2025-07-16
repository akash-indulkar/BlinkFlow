import { useState } from "react";
import { Input } from "../Input";
import { BaseButton } from "../buttons/BaseButton";

export const ClickUpTaskActionConfigurer = ({ setMetadata }: {
    setMetadata: (params: any) => void;
}) => {
    const [APIToken, setAPIToken] = useState("");
    const [listID, setListID] = useState("");
    const [taskName, setTaskName] = useState("");
    const handleSubmit = () => {
        setMetadata({
            APIToken,
            listID,
            taskName
        });
    };

    return <form onSubmit={handleSubmit}>
        <Input label={"API Token"} type={"password"} placeholder="Token" onChange={(e) => {
            setAPIToken(e.target.value)
            e.target.setCustomValidity("")
        }}></Input>
        <Input label={"List ID"} type={"text"} placeholder="List ID" onChange={(e) => {
            setListID(e.target.value)
            e.target.setCustomValidity("")
        }}></Input>
        <Input label={"Task Name"} type={"text"} placeholder="Name" onChange={(e) => {
            setTaskName(e.target.value)
            e.target.setCustomValidity("")
        }}></Input>

        <div className="pt-4 pb-4 border-black text-xs text-gray-500">To enable integration with your ClickUp Workspace, please provide your <strong>API Token</strong>, <strong>Task Name</strong> and <strong>List ID</strong> where you want task to be created. You need to <a href="https://help.clickup.com/hc/en-us/articles/6310502590487-Create-a-new-Workspace"
                target="_blank" rel="noopener noreferrer"
                className="text-blue-600 underline">create a ClickUp WorkSpace</a> first.
            To know how to get a your API Token, <a
                href="https://clickup.com/blog/clickup-api/"
                target="_blank"
                rel="noopener noreferrer"
                className="text-blue-600 underline"
            >click here</a>.
            To find the List ID, right-click the List in your Sidebar, select Copy link, and paste the link in your URL. The last string in the URL is your List ID.
            Your API Token will be stored securely and only used to access your authorized ClickUp workspace.
        </div>
        <BaseButton isInactive={!APIToken.trim() || !listID.trim() || !taskName.trim()}>Submit</BaseButton>
    </form>
}