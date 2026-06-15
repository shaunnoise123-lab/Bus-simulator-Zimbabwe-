# 🚌 Bus Simulator Zimbabwe

A comprehensive 3D bus simulator game featuring Zimbabwe's roads, economy, traffic systems, and authentic transportation experience.

## 🎮 Game Features

### World & Environment
- **Real Zimbabwe Map**: All major cities (Harare, Bulawayo, Mutare, Masvingo) and villages
- **Dynamic Roads**: Main highways, dusty village roads, dangerous off-road routes, damaged roads with potholes
- **Weather System**: Rain, dust storms, floods (affecting road conditions)
- **Lighting System**: Day/night cycles with realistic vehicle and building lighting
- **Vegetation**: Detailed savanna and mountainous terrain with bushes and trees
- **Time System**: 1 real hour = 24 game hours

### Vehicle Systems
- **59+ Bus Types**: DAF, Marcopolo, Irizar, MAN, Benz, Zhong Tong, and many more
- **Realistic Damage**: Collision deformation, cracked windshields, body damage
- **Tire System**: Punctures (especially when overloaded), tire replacement mechanics
- **Overloading**: Extra revenue but risks punctures and speed reduction
- **Original Sounds**: Authentic engine sounds, horn sounds, window rattling for old buses
- **Cargo Trailers**: Some buses can carry cargo
- **Customization**: Luggage carriers, lights, horns, entertainment systems

### Traffic & Rules
- **AI Traffic**: Cars, trucks, buses from other companies following traffic rules
- **Speed Cameras**: Over-speeding results in expensive tickets
- **Traffic Lights**: Crossing red lights results in fines
- **Police Roadblocks**: Searches for illegal goods, fines for violations
- **Police Chases**: Failure to stop results in pursuit and arrest
- **Accidents**: Dynamic accident events with police and ambulances
- **Competition**: Beautiful, fast buses from other companies competing for passengers

### Economy System
- **Zimbabwe Currency**: Dual currency system (USD/ZiG)
- **Passenger Revenue**: Earn money per passenger
- **Fuel Stations**: Real-world fuel costs
- **Maintenance**: Engine repair, tire replacement, paint restoration, rust repair
- **Career Path**: Start poor with old DAF buses → Build reputation → Get hired by better companies → Buy own bus → Own company
- **Company Management**: Hire drivers, hosts/hostess, manage fleet
- **Illegal Goods**: High-risk, high-reward cargo trading
- **Bail System**: Get arrested at roadblocks (3 game hours or pay half bank as bail)

### Interaction & Gameplay
- **Cockpit Interaction**: Interactive buttons and switches specific to each bus
- **Walk Inside/Outside**: Full bus exploration
- **Tire Repair**: Fix punctures on the road
- **Engine Repair**: Fix breakdowns in the middle of the road
- **Tow Service**: Unavailable due to network limitations (self-repair required)
- **Night Dangers**: Thieves at stops who steal half your bank
- **Bus Stops**: Fuel stations, service stations, rest areas, passenger pickup zones
- **Off-Road Routes**: Dangerous alternatives to main roads to avoid roadblocks

### Graphics & Performance
- **Adjustable Graphics**: Quality settings for smooth gameplay
- **Android 5+**: Optimized for low-end devices
- **Size**: < 4GB
- **RAM**: Minimum 1GB required
- **Smooth Gameplay**: No bugs, optimized rendering

## 🛠️ Tech Stack

- **Language**: Kotlin
- **Game Engine**: Custom 3D Engine with Canvas/OpenGL rendering
- **Graphics**: Hardware-accelerated rendering with adjustable quality
- **Database**: SQLite with Room Persistence Library
- **Audio**: Android AudioTrack for real-time sound synthesis
- **Physics**: Custom physics engine for realistic vehicle behavior
- **UI Framework**: Jetpack Compose with custom rendering

## 📦 Project Structure

```
Bus-simulator-Zimbabwe/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/
│   │   │   │   ├── core/
│   │   │   │   │   ├── GameEngine.kt
│   │   │   │   │   ├── PhysicsEngine.kt
│   │   │   │   │   └── RenderEngine.kt
│   │   │   │   ├── world/
│   │   │   │   │   ├── WorldManager.kt
│   │   │   │   │   ├── MapData.kt
│   │   │   │   │   └── WeatherSystem.kt
│   │   │   │   ├── vehicles/
│   │   │   │   │   ├── BusModel.kt
│   │   │   │   │   ├── VehiclePhysics.kt
│   │   │   │   │   └── DamageSystem.kt
│   │   │   │   ├── traffic/
│   │   │   │   │   ├── TrafficManager.kt
│   │   │   │   │   ├── AIDriver.kt
│   │   │   │   │   └── RuleEnforcement.kt
│   │   │   │   ├── economy/
│   │   │   │   │   ├── EconomyManager.kt
│   │   │   │   │   ├── Currency.kt
│   │   │   │   │   └── PlayerProfile.kt
│   │   │   │   ├── ui/
│   │   │   │   │   ├── GameScreen.kt
│   │   │   │   │   ├── Cockpit.kt
│   │   │   │   │   └── HUD.kt
│   │   │   │   ├── audio/
│   │   │   │   │   ├── SoundEngine.kt
│   │   │   │   │   └── BusSounds.kt
│   │   │   │   ├── data/
│   │   │   │   │   ├── GameDatabase.kt
│   │   │   │   │   └── Entities.kt
│   │   │   │   └── MainActivity.kt
│   │   │   └── res/
│   │   │       ├── values/
│   │   │       └── drawable/
│   ├── build.gradle.kts
│   └── ...
├── gradle/
├── build.gradle.kts
└── settings.gradle.kts
```

## 🎯 Development Roadmap

### Phase 1: Foundation ✓
- [ ] Project setup and structure
- [ ] Core game engine
- [ ] Render system

### Phase 2: World
- [ ] Zimbabwe map with cities/villages
- [ ] Road system (main, village, off-road)
- [ ] Weather system
- [ ] Time management

### Phase 3: Vehicles
- [ ] Bus models (59+ types)
- [ ] Vehicle physics
- [ ] Damage system
- [ ] Sound system

### Phase 4: Traffic
- [ ] AI traffic system
- [ ] Traffic rules and enforcement
- [ ] Police systems
- [ ] Accidents and emergencies

### Phase 5: Gameplay
- [ ] Economy system
- [ ] Career progression
- [ ] Company management
- [ ] Illegal goods trading

### Phase 6: Polish
- [ ] Graphics optimization
- [ ] Performance tuning
- [ ] Bug fixes
- [ ] UI refinement

## 🚀 Getting Started

### Requirements
- Android Studio (latest)
- Android SDK 21+ (API Level 5+)
- Kotlin 1.8+
- Gradle 7.0+

### Setup
```bash
# Clone the repository
git clone https://github.com/shaunnoise123-lab/Bus-simulator-Zimbabwe-.git
cd Bus-simulator-Zimbabwe-

# Open in Android Studio and sync Gradle
# Run on Android 5.0+ device or emulator
```

### Build & Run
```bash
# Build
./gradlew build

# Run
./gradlew installDebug
```

## 📊 Game Statistics

- **Supported Android Versions**: 5.0+ (API 21+)
- **Bus Types**: 59+
- **Zimbabwe Cities**: 10+
- **Villages/Growth Points**: 50+
- **Road Types**: 4 (highway, village, off-road, damaged)
- **Weather Conditions**: 5 (clear, rain, dust, flood, night)
- **Vehicle Types**: 6+ (buses, trucks, cars, ambulances, police vehicles)
- **Maximum File Size**: < 4GB
- **Minimum RAM**: 1GB

## 🎮 Controls

### Driving Mode
- **Steering**: Tilt device or use steering wheel
- **Accelerate**: Touch gas pedal
- **Brake**: Touch brake pedal
- **Horn**: Press horn button
- **Lights**: Toggle light switches
- **Doors**: Open/close button
- **Gears**: Manual, Semi-Auto, Auto modes

### Walking Mode
- **Move**: Swipe/joystick
- **Interact**: Tap doors, windows, controls
- **Exit Bus**: Tap exit button

## 💰 Economy Features

- **Passenger Fares**: USD 1-5 depending on distance
- **Fuel Costs**: USD 0.50-2.00 per liter
- **Bus Prices**: USD 5,000 (old DAF) to USD 150,000 (premium Marcopolo)
- **Illegal Goods**: USD 500-5,000 per trip
- **Fines**: Speed camera (USD 50), Red light (USD 75), Roadblock (USD 100)
- **Jail Bail**: Half your current bank balance

## 📝 License

MIT License - See LICENSE file for details

## 👨‍💻 Author

Shaunnoise123-Lab

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## 📧 Contact

For questions or suggestions, please open an issue on GitHub.

---

**Status**: 🚧 Under Active Development

**Last Updated**: June 2026

