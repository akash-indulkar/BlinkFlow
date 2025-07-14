import { useState } from "react";
import { Input } from "../Input";
import { BaseButton } from "../buttons/BaseButton";

export const TelegramActionConfigurer = ({ setMetadata }: {
    setMetadata: (params: any) => void;
}) => {
    const [botToken, setBotToken] = useState("");
    const [channelID, setChannelID] = useState("");
    const handleSubmit = () => {
        setMetadata({
            botToken,
            channelID
        });
    };

    return <form onSubmit={handleSubmit}>
        <Input label={"Bot Token"} type={"password"} placeholder="Token" onChange={(e) => {
            setBotToken(e.target.value)
            e.target.setCustomValidity("")
        }}></Input>
        <Input label={"Target Telegram Channel Username"} type={"text"} placeholder="Channel Username" onChange={(e) => {
            setChannelID(e.target.value)
            e.target.setCustomValidity("")
        }}></Input>
        <div className="pt-4 pb-4 border-black text-xs text-gray-500">To enable integration with your Telegram channel, please provide your <strong>Bot Token</strong> and your <strong>Channel Username</strong> of the target Telegram channel.
            You must first <a href="https://core.telegram.org/bots/features#creating-a-new-bot"
                target="_blank" rel="noopener noreferrer"
                className="text-blue-600 underline">create a Telegram Bot</a>,
            using <a
                href="https://t.me/BotFather"
                target="_blank"
                rel="noopener noreferrer"
                className="text-blue-600 underline"
            > @BotFather</a>
            {" "}, then add the bot to your target Telegram channel as an administrator with permission to post messages. You can find your channel username in your channel's invite link as "https://t.me/yourchannelusername".
            Your token will be stored securely and only used to access your authorized channel.
        </div>
        <BaseButton isInactive={!botToken.trim() || !channelID.trim()}>Submit</BaseButton>
    </form>
}