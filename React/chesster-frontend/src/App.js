import logo from './logo.svg';

function App() {
    return (
        <div className="flex flex-col items-center justify-center min-h-screen bg-gray-900 text-white">
            <img
                src={logo}
                alt="logo"
                className="h-40 animate-spin-slow pointer-events-none mb-8"
            />
            <p className="text-lg">
                Edit <code className="bg-gray-800 px-1 rounded">src/App.js</code> and save to reload.
            </p>
            <a
                href="https://reactjs.org"
                target="_blank"
                rel="noopener noreferrer"
                className="mt-4 text-blue-400 hover:text-blue-600 transition-colors"
            >
                Learn React
            </a>
        </div>
    );
}

export default App;
