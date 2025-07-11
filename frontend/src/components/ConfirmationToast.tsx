export const ConfirmationToast = ({
    message,
    onConfirm,
    onCancel,
}: {
    message: string;
    onConfirm: () => void;
    onCancel: () => void;
}) => {
    return <div className="bg-[#fffdf9] position-absolute fixed top-[200px] right-[500px] text-gray-700 p-6 rounded-lg shadow-lg w-[90vw] max-w-md border border-gray-300  ">
        <div className="flex flex-col items-center">
            <button type="button" onClick={() => {
                onCancel()}} className="text-gray-400 absolute top-2.5 right-2.5 bg-transparent hover:bg-gray-200 hover:text-gray-900 rounded-lg text-sm p-1.5 ml-auto inline-flex items-center dark:hover:bg-gray-600 dark:hover:text-white" data-modal-toggle="deleteModal">
                <svg aria-hidden="true" className="w-5 h-5" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z" clip-rule="evenodd"></path></svg>
                <span className="sr-only">Close modal</span>
            </button>
            <svg className="text-gray-400 w-12 h-12 mb-3" fill="currentColor" viewBox="0 0 20 20">
                <path fillRule="evenodd" d="M9 2a1 1 0 00-.894.553L7.382 4H4a1 1 0 000 2v10a2 2 0 002 2h8a2 2 0 002-2V6a1 1 0 100-2h-3.382l-.724-1.447A1 1 0 0011 2H9zM7 8a1 1 0 012 0v6a1 1 0 11-2 0V8zm5-1a1 1 0 00-1 1v6a1 1 0 102 0V8a1 1 0 00-1-1z" clipRule="evenodd" />
            </svg>
            <p className="text-lg text-center mb-4">{message}</p>
            <div className="flex gap-4">
                <button
                    onClick={() => {
                        onCancel();
                    }}
                    className="px-4 py-2 text-sm text-gray-700 border border-gray-300 rounded hover:bg-gray-100 ">
                    No, Cancel
                </button>
                <button
                    onClick={() => {
                        onConfirm();
                    }}
                    className="px-4 py-2 text-sm text-white bg-red-600 hover:bg-red-700 rounded">
                    Yes, I'm Sure
                </button>
            </div>
        </div>
    </div>
};
