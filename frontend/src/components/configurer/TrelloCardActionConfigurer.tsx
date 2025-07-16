import { useState } from "react";
import { Input } from "../Input";
import { BaseButton } from "../buttons/BaseButton";

export const TrelloCardActionConfigurer = ({ setMetadata }: {
    setMetadata: (params: any) => void;
}) => {
    const [APIKey, setAPIKey] = useState("");
    const [APIToken, setAPIToken] = useState("");
    const [listID, setListID] = useState("");
    const [cardName, setCardName] = useState("");
    const handleSubmit = () => {
        setMetadata({
            APIKey,
            APIToken,
            listID,
            cardName
        });
    };

    return <form onSubmit={handleSubmit}>
        <Input label={"API Key"} type={"password"} placeholder="Key" onChange={(e) => {
            setAPIKey(e.target.value)
            e.target.setCustomValidity("")
        }}></Input>
        <Input label={"API Token"} type={"password"} placeholder="Token" onChange={(e) => {
            setAPIToken(e.target.value)
            e.target.setCustomValidity("")
        }}></Input>
        <Input label={"List ID"} type={"text"} placeholder="List ID" onChange={(e) => {
            setListID(e.target.value)
            e.target.setCustomValidity("")
        }}></Input>
        <Input label={"Card Name"} type={"text"} placeholder="Name" onChange={(e) => {
            setCardName(e.target.value)
            e.target.setCustomValidity("")
        }}></Input>

        <div className="pt-4 pb-4 border-black text-xs text-gray-500">To enable integration with your Trello Workspace, please provide your <strong>API Key</strong>, <strong>API Token</strong>, <strong>Card Name</strong> and <strong>List ID</strong> where you want card to be created. You need to <a href="https://trello.com/guide/create-project"
                target="_blank" rel="noopener noreferrer"
                className="text-blue-600 underline">create a Trello Board</a> first.
            For generating an API key and API Token, you first need to have created a <a
                href="https://trello.com/power-ups/admin"
                target="_blank"
                rel="noopener noreferrer"
                className="text-blue-600 underline"
            >Trello Power-Up</a>.
            To know how to get a you API key and generate API Token, <a
                href="https://developer.atlassian.com/cloud/trello/guides/rest-api/api-introduction/"
                target="_blank"
                rel="noopener noreferrer"
                className="text-blue-600 underline"
            >click here</a>.
            To know how to find your List ID, <a
                href="https://docs.pixiebrix.com/integrations/trello/find-board-and-list-ids-in-trello"
                target="_blank"
                rel="noopener noreferrer"
                className="text-blue-600 underline"
            >click here</a>.
            Your API Key and API Token will be stored securely and only used to access your authorized Trello workspace.
        </div>
        <BaseButton isInactive={!APIKey.trim() || !APIToken.trim() || !listID.trim() || !cardName.trim()}>Submit</BaseButton>
    </form>
}