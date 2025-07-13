import { useState } from "react";
import { Input } from "../Input";
import { BaseButton } from "../buttons/BaseButton";

export const SlackActionConfigurer = ({ setMetadata }: {
    setMetadata: (params: any) => void;
}) => {
    const [OAuthToken, setOAuthToken] = useState("");
    const [channelID, setChannelID] = useState("");
    const handleSubmit = () => {
        setMetadata({
            OAuthToken,
            channelID
        });
    };

    return <form onSubmit={handleSubmit}>
        <Input label={"Bot User OAuth Token"} type={"password"} placeholder="Your Secret" onChange={(e) => {
            setOAuthToken(e.target.value)
            e.target.setCustomValidity("")
        }}></Input>
        <Input label={"Target Slack Channel ID"} type={"text"} placeholder="Page ID" onChange={(e) => {
            setChannelID(e.target.value)
            e.target.setCustomValidity("")
        }}></Input>
        <div className="pt-4 pb-4 border-black text-xs text-gray-500">To enable integration with your Slack workspace, please provide your <strong>Bot User OAuth Token</strong> and the <strong>Channel ID</strong> of the target Slack channel.
            You must first <a href="https://api.slack.com/apps"
                target="_blank" rel="noopener noreferrer"
                className="text-blue-600 underline">create a Slack App/Bot</a>,
            add permissions by <a
                href="https://api.slack.com/scopes"
                target="_blank"
                rel="noopener noreferrer"
                className="text-blue-600 underline"
            > Scopes</a>
            {" "}and install it to your workspace to generate the token.
            Once installed, make sure to invite the App/Bot to the channel where you want it to send messages.
            Your token will be stored securely and only used to access your authorized channel. To know more about Slack Apps/Bot, and how to setup one in your channel,   {" "}
            <a
                href="https://api.slack.com/quickstart"
                target="_blank"
                rel="noopener noreferrer"
                className="text-blue-600 underline"
            >
                Click here.
            </a>
            {" "} Need help finding your Channel ID? <a
                href="https://help.socialintents.com/article/148-how-to-find-your-slack-team-id-and-slack-channel-id"
                target="_blank"
                rel="noopener noreferrer"
                className="text-blue-600 underline"
            >
                Click here.
            </a> to learn more.
        </div>
        <BaseButton isInactive={!OAuthToken.trim() || !channelID.trim()}>Submit</BaseButton>
    </form>
}