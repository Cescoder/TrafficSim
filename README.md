# 🚗 Terminal Traffic Simulation

A simple multithreaded traffic simulation in Java that models the behavior of multiple cars on a one-dimensional road and displays their positions in a terminal-based visualization.

---

## 🛠 Features

- 🚘 **Vehicle simulation** with position, velocity, acceleration, and realistic spacing.
- 🧠 **Dynamic acceleration model** that adapts to the car ahead (Intelligent Driver Model-inspired).
- 🖥 **Terminal-based visualization** with:
  - Dynamic centering on a selected vehicle
  - Adaptive zoom based on road coverage
  - Visual markers every 100m and km
- 🧵 **Multithreaded architecture**: simulation and rendering run concurrently for smooth updates.
- ⏱ Adjustable **time speed** for real-time or faster-than-real-time simulation.

---

## 📦 Structure

- `Car.java`: Defines the vehicle dynamics.
- `Universe.java`: Simulation engine; updates vehicle states over time.
- `TerminalView.java`: Renders the road and vehicles in the terminal.
- `Main.java`: Entry point; sets up and runs simulation/view in parallel threads.

---

### ✅ Prerequisites

- Java 11+ installed
- Terminal that supports monospaced characters

---

### 👥 Authors

- [Cescoder](https://github.com/Cescoder)
- [gass-ita](https://github.com/gass-ita)
