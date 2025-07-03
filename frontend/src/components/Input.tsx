
export const Input = ({ label, placeholder, onChange, type }: {
    label: string;
    placeholder: string;
    onChange: (e: any) => void;
    type: string
}) => {
    return <div>
        <div className="text-sm pt-2 pb-2 flex">
            <label>{label}</label><p className="pl-1 text-red-500">*</p>
        </div>
        <input required type={type}
            onInvalid={(e) => {
                const target = e.target as HTMLInputElement;
                if(target.type == "email") {
                    target.setCustomValidity("Please enter a valid email address (e.g., user@example.com)");
                }
            }} className="border text-sm rounded px-4 py-2 w-full text-sm text-gray-900 bg-gray-50 rounded border border-gray-300 focus:outline focus:outline-indigo-500 invalid:text-red-600" placeholder={placeholder} onChange={onChange} />
    </div>
}