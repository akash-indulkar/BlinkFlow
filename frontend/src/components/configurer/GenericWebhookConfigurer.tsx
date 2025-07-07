import { useState } from "react";
import { Input } from "../Input";
import { BaseButton } from "../buttons/BaseButton";

export const GenericWebhookConfigurer = ({ setMetadata }: {
    setMetadata: (params: any) => void;
}) => {
    const [secret, setSecret] = useState("");
    const handleSubmit = () => {
        setMetadata({
           secret
        });
    };
    return <form onSubmit={handleSubmit}>
        <Input label={"Secret"} type={"password"} placeholder="Secret" onChange={(e) => setSecret(e.target.value)}></Input>
        <div className="pt-4 border-black text-xs text-gray-500">This secret will be used to verify incoming Webhook requests using the SHA-256 algorithm.
            Make sure to use the same secret and algorithm (SHA-256) when registering the Webhook on the client platform. When creating a secret, you should choose a random string of text with high entropy.
            The platform should send a signature of the payload in the request headers, generated using this secret. To know more about Webhook secret, <a
                href="https://www.techtarget.com/searchapparchitecture/tip/Webhook-security-Risks-and-best-practices-for-mitigation"
                target="_blank"
                rel="noopener noreferrer"
                className="text-blue-600 underline"
            >
               Click here.
            </a> 
        </div>
        <div className="pt-4">
            <BaseButton isInactive={!secret.trim()}>Submit</BaseButton>
        </div>
    </form>
}
