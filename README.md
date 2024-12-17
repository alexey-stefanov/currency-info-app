# CurrencyInfoApp

CurrencyInfoApp — это приложение для просмотра курсов валют, которое позволяет пользователям:
- Просматривать актуальные курсы валют.
- Отмечать валютные пары как "избранные".
- Применять сортировку и фильтрацию данных по различным параметрам.

## 📱 Основные возможности
1. **Курсы валют**:
   - Актуальные данные о курсах валют с использованием API [Apilayer](https://apilayer.com).
   - Возможность выбора базовой валюты.

2. **Избранные валютные пары**:
   - Возможность сохранения валютных пар как избранных для быстрого доступа.

3. **Фильтрация и сортировка**:
   - Сортировка курсов по коду валюты или значению (по возрастанию/убыванию).
   - Возможность выбора фильтров на отдельном экране.

4. **Интуитивный дизайн**:
   - Реализовано с использованием современных компонентов Jetpack Compose.
   - Поддержка тем и адаптация интерфейса под Material Design 3.

## 🛠 Используемые технологии
- **Язык**: Kotlin
- **Архитектура**: MVVM (Model-View-ViewModel)
- **UI**: Jetpack Compose
- **Навигация**: Compose Navigation
- **База данных**: Room
- **Зависимости**: Hilt для DI (Dependency Injection)
- **Работа с сетью**:
  - Retrofit для HTTP-запросов.
  - Kotlin Coroutines для асинхронных запросов.

## 🚀 Как запустить проект
### Шаги для запуска приложения локально:
1. Клонируйте репозиторий:
   ```bash
   git clone https://github.com/alexey-stefanov/currency-info-app.git
   cd CurrencyInfoApp
2. Добавьте API ключ в local.properties
   ```bash
   API_KEY=<Ваш API ключ от Apilayer>
3. Синхронизируйте проект с Gradle.
4. Запустите приложение через Android Studio.

## 📝 Фичи для будущих обновлений
  - Локализация интерфейса на другие языки.
  - Поддержка тёмной темы.
  - Возможность настройки уведомлений для изменения курса избранных валют.