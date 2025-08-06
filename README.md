# 🤖 Chatbot-android

A simple Android chatbot app that connects to an open-source Large Language Model (LLM) using the [OpenRouter API](https://openrouter.ai). Built with Kotlin in Android Studio, this project showcases how to build a mobile messaging interface that sends prompts to a free LLM and displays AI responses in styled chat bubbles.

---

## 📸 Screenshots

<p align="center">
  <img src="https://github.com/user-attachments/assets/00816cd0-d620-4b88-9af6-83dc2752a210" alt="AppHomeScreen" width="45%" />
  <img src="https://github.com/user-attachments/assets/0e41d54f-3904-4ecf-8c2b-8994eebe511a" alt="ChatInAction" width="45%" />
</p>

## ✨ Features

- Built entirely in **Android Studio** with **Kotlin**
- Uses **RecyclerView** for scrolling chat history
- Sends messages to **OpenRouter's LLMs** (e.g. Mistral) via REST API
- Displays messages in clean, chat-style **speech bubbles**
- Fully responsive and keyboard-aware
- Beginner-friendly and easy to extend

---

## 🧱 Tech Stack

| Tool        | Purpose                    |
|-------------|-----------------------------|
| Kotlin      | Programming language        |
| Android SDK | UI and app logic            |
| OpenRouter  | API for LLM inference       |
| OkHttp      | HTTP networking             |
| JSON        | Request/response formatting |

---

## 🛠 Setup Instructions

1. **Clone the repo**

```bash
git clone https://github.com/yourusername/Chatbot-android.git
cd Chatbot-android
```

2.	**Open the project in Android Studio**
3.	**Add your OpenRouter API key**

In MainActivity.kt, replace:

``` private val apiKey = "your_openrouter_api_key" ```

with your actual key (see below for how to get it)



##🔐 How to Get an OpenRouter API Key

1.	Go to https://openrouter.ai
2.	Sign up for a free account
3.	Visit API Key Settings
4.	Generate a key and copy it


** 🧪 How to Use the App**

1.	Type a message in the input field.
2.	Press Send.
3.	The chatbot replies using a selected LLM model.
4.	Messages appear in styled bubbles:
    
    Your message → right-aligned (green)
    AI response → left-aligned (white)

## 🔧 Customization

You can change the LLM model in MainActivity.kt:

put("model", "mistralai/mistral-7b-instruct")

Other supported models are listed here:
👉 https://openrouter.ai/docs#models


## 🧼 Security Notes
•	Do not commit your API key in public repositories.
•	Use .gitignore to exclude local.properties or move your API key to gradle.properties.


## 📄 License

This project is open-source under the MIT License.



## 🙌 Acknowledgements
•	OpenRouter.ai
•	Mistral
•	OpenAI’s ChatGPT UI as UI inspiration


