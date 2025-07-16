import { useState } from "react";
import { Input } from "../Input";
import { BaseButton } from "../buttons/BaseButton";

export const AsanaTaskActionConfigurer = ({ setMetadata }: {
    setMetadata: (params: any) => void;
}) => {
    const [personalAccessToken, setPersonalAccessToken] = useState("");
    const [projectID, setProjectID] = useState("");
    const handleSubmit = () => {
        setMetadata({
            personalAccessToken,
            projectID
        });
    };

    return <form onSubmit={handleSubmit}>
        <Input label={"Personal Access Token"} type={"password"} placeholder="Token" onChange={(e) => {
            setPersonalAccessToken(e.target.value)
            e.target.setCustomValidity("")
        }}></Input>
        <Input label={"Project ID"} type={"text"} placeholder="Project ID" onChange={(e) => {
            setProjectID(e.target.value)
            e.target.setCustomValidity("")
        }}></Input>
        <div className="pt-4 pb-4 border-black text-xs text-gray-500">To enable integration with your Asana project, please provide your <strong>Personal Access Token</strong> and <strong>Project ID</strong> where you want tasks to be created. You need to create a project and generate a Personal Access Token first.
            You can generate a Personal Access Token from <a href="https://app.asana.com/0/my-apps"
                target="_blank" rel="noopener noreferrer"
                className="text-blue-600 underline">Asana's Developer Console</a>.
            To know how to create a Project, <a
                href="https://help.asana.com/s/article/how-to-create-a-project?language=en_US"
                target="_blank"
                rel="noopener noreferrer"
                className="text-blue-600 underline"
            >click here</a>.
            To know how to generate a Personal Access Token, <a
                href="https://developers.asana.com/docs/personal-access-token"
                target="_blank"
                rel="noopener noreferrer"
                className="text-blue-600 underline"
            >click here</a>.
            You can find your Project ID in your URL after /project/ as "https://app.asana.com/1/1111111111111/project/Your_Project_ID" when you open your project.
            Your token will be stored securely and only used to access your authorized project.
        </div>
        <BaseButton isInactive={!personalAccessToken.trim() || !projectID.trim()}>Submit</BaseButton>
    </form>
}