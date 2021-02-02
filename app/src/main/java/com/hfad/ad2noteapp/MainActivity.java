package com.hfad.ad2noteapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.hfad.ad2noteapp.models.Note;
import com.hfad.ad2noteapp.ui.board.BoardAdapter;
import com.hfad.ad2noteapp.ui.home.HomeFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private NavController navController;
    private AppBarConfiguration appBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initNavController();

        Prefs prefs = new Prefs(this);

        if(!prefs.isShown()) {
            navController.navigate(R.id.boardFragment);
        }
    }

    private void initNavController() {
        BottomNavigationView navView = findViewById(R.id.nav_view);

        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home,
                R.id.navigation_dashboard,
                R.id.navigation_notifications,
                R.id.profileFragment)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller,
                                             @NonNull NavDestination destination,
                                             @Nullable Bundle arguments) {
                ArrayList<Integer> list = new ArrayList<>();
                list.add(R.id.navigation_home);
                list.add(R.id.navigation_dashboard);
                list.add(R.id.navigation_notifications);
                list.add(R.id.profileFragment);

                if(list.contains(destination.getId())){
                    navView.setVisibility(View.VISIBLE);
                }else{
                    navView.setVisibility(View.GONE);
                }

                if(destination.getId()== R.id.boardFragment){

                    getSupportActionBar().hide();

                } else {

                    getSupportActionBar().show();

                }
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}

/*
            HM - 1

        1. Добавить 4 фрагмент с названием Profile
        2. Добавить imageView в ProfileFragment
        3. При нажатии открыть галерею для выбора картинки
        4. Установить в ImageView



            HM - 2

        1. Добавить 10 записей
        2. Удалить запись через AlertDialog на долгое нажатие
        3. Добавить поле createdAt и показать в листе



            HM - 3

        1. Показывать кнопку только на 3 странице
        2. Добавить описания на слайды
        3. Добавить три разных картинок на слайды
        4. Добавить меню в HomeFragment для очистки настроек
        5. Добавить кнопку skip на верхний правый угол, которая не двигается



            HM - 4

        1. Удаление записи из БД
        2. Редактирование записи
        3. Кнопка в меню для сортировки по алфавиту (одна кнопка)
        Bonus: Сортировка по времени



            HM - 5

        1. Анимация на board
        2. Точки на страницы (TabLayout)
        3. Профиль фрагмент дизайн


            HM - 6

        1. Окно для ввода кода из СМС
        2. Обратный отсчет, при завершении показать первое окно с красным текстом "Проверить номер"
        3. После Board показывать PhoneFragment


            HM - 7

        1. Добавить FireStore
        2. При добавлении записи в Room, также добавлять в FireStore
        3. При удалении удалять с FireStore
        4. Удаление записи через Console



            HM - 8

        1. id Note должен быть стринг и должен сохранять docId из FireStore
        2. Удаление записи из FireStore
        3. Редактирование


 */