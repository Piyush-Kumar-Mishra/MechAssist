# 🚗 MechAssist

### ⧉ The Problem

Vehicle breakdowns and urgent repair needs happen unexpectedly, leading to common frustrations:

* Hard to quickly find reliable nearby mechanics and garages
* Unclear whether a shop is currently open or closed
* Repetitively entering vehicle number and contact details for every service request
* No centralized place to track previous service requests

---

### ⧉ The Solution

MechAssist simplifies mechanic discovery and vehicle service booking into a clean, seamless experience:

* Fetches real-time mechanic listings with distances, ratings, and operating hours via REST API
* Quick-filter garages by Open/Closed status and star ratings
* Multi-field search across garage names, locations, addresses, and services
* Auto-fills service booking requests using user profile data stored in local Room DB
* Tracks complete booking history locally with offline access

| Kotlin | Jetpack Compose | Material 3 | Clean Architecture |
| ------ | --------------- | ---------- | ------------------ |

| Retrofit | Room Database | MVVM | Coroutines & Flow | Hilt DI |
| -------- | ------------- | ---- | ----------------- | ------- |

---

<table>
<tr>
<td width="60%">

<h1>Dashboard Screen</h1>

The dashboard acts as the primary hub for exploring nearby mechanics and garages.

<ul>
<li>Fetches real-time garage data from REST API with loading, error, and empty states.</li>
<li><b>Multi-Field Search:</b> Real-time search across garage name, area, street address, and services with a one-tap clear button.</li>
<li><b>Filter Chips:</b> Filter by <code>Open</code>, <code>Closed</code>, <code>4+ Rating</code>, <code>3+ Rating</code>, and <code>2+ Rating</code>.</li>
<li><b>Status Badges:</b> Visual Open (Green) and Closed (Red) badges along with star ratings and distance.</li>
</ul>

</td>

<td width="40%">

<img width="400" height="900" alt="Dashboard Screen" src="https://github.com/user-attachments/assets/51d4f5fc-02f7-4933-bfba-ea831529398a" />

</td>
</tr>
</table>

---

<table>
<tr>

<td width="40%">

<img width="400" height="900" alt="Mechanic Details and Booking Screen" src="https://github.com/user-attachments/assets/954a4e9b-4f5e-4e24-a4ea-ad6e3a73d219" />

</td>

<td width="60%">

<h1>Mechanic Details & Booking</h1>

Detailed garage information and a pre-filled booking flow.

<ul>
<li><b>Garage Details:</b> Full address, operating hours, contact phone number, and service tags.</li>
<li><b>Auto-Filled Form:</b> Customer name, phone number, and vehicle number are pre-filled from local Room profile so you don't re-type info.</li>
<li><b>Service Selection:</b> Dropdown menu populated specifically with that mechanic's offered services.</li>
<li><b>Input Validation & Confirmation:</b> 10-digit phone verification, mandatory field checks, and an alert dialog on submission.</li>
</ul>

</td>
</tr>
</table>

---

<table>
<tr>

<td width="60%">

<h1>History & Profile Screens</h1>

Offline tracking and profile management powered by Room Database.

<ul>
<li><b>Booking History:</b> Reactive stream (<code>Flow</code>) listing all past service requests with formatted timestamps and service types.</li>
<li><b>Profile Management:</b> Store and edit Customer Name, Phone, Vehicle Number, Vehicle Model, and City.</li>
<li><b>Smart Routing:</b> Splash screen detects existing profiles and routes returning users straight to the dashboard.</li>
<li><b>Bottom Navigation:</b> 3 persistent tabs — <code>Dashboard</code>, <code>History</code>, and <code>Profile</code>.</li>
</ul>

</td>

<td width="40%">

<img width="400" height="900" alt="History and Profile Screens" src="https://github.com/user-attachments/assets/92095b3f-441b-4035-b035-d2de610002bb" />

</td>
</tr>
</table>

---

## 🛠️ Tech Stack & Architecture

* **Architecture**: MVVM (Model-View-ViewModel) + Repository Pattern
* **UI**: 100% Jetpack Compose with Material 3 (White Minimalist Theme)
* **Dependency Injection**: Dagger Hilt
* **Network**: Retrofit 2 + Gson Converter
* **Local Database**: Room Database (SQLite)
* **Async**: Kotlin Coroutines & StateFlow / Flow
* **Navigation**: Jetpack Navigation Compose

```text
┌────────────────────────────────────────────────────────┐
│                   UI Layer (Compose)                   │
│   Screens: Splash, ProfileSetup, Dashboard, Detail,   │
│            BookingForm, History, Profile              │
└───────────────────────────▲────────────────────────────┘
                            │ StateFlow / Events
┌───────────────────────────┴────────────────────────────┐
│                    ViewModel Layer                     │
│   MechanicViewModel, BookingViewModel,                │
│   HistoryViewModel, ProfileViewModel                  │
└───────────────────────────▲────────────────────────────┘
                            │ Coroutines / Flow
┌───────────────────────────┴────────────────────────────┐
│                    Repository Layer                    │
│   MechanicRepository, BookingRepository,              │
│   ProfileRepository                                    │
└─────────────▲────────────────────────────▲─────────────┘
              │                            │
┌─────────────┴──────────────┐ ┌───────────┴─────────────┐
│    Remote Data (Retrofit)  │ │   Local Database (Room) │
│    - MechanicApi           │ │    - AppDatabase        │
│    - MechanicDto           │ │    - ProfileDao / Entity│
│                            │ │    - BookingDao / Entity│
└────────────────────────────┘ └─────────────────────────┘
```
