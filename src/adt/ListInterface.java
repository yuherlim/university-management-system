/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package adt;

import java.util.Iterator;

/**
 * Adapted from Frank M. Carrano
 * @author Kho Ka Jie, Lim Yu Her, Ong Cheng Leong, Sia Yeong Sheng
 * @version 2.0
 */
public interface ListInterface<T> {

  public Iterator<T> getIterator();
    
  /**
   * Task: Adds a new entry to the end of the list. Entries currently in the
   * list are unaffected. The list's size is increased by 1.
   *
   * @param newEntry the object to be added as a new entry
   * @return true if the addition is successful, or false if the list is full
   */
  public boolean add(T newEntry);

  /**
   * Task: Adds a new entry at a specified position within the list. Entries
   * originally at and above the specified position are at the next higher
   * position within the list. The list's size is increased by 1.
   *
   * @param newPosition an integer that specifies the desired position of the
   * new entry
   * @param newEntry the object to be added as a new entry
   * @return true if the addition is successful, or false if either the list is
   * full, newPosition < 1, or
   *          newPosition > getNumberOfEntries()+1
   */
  public boolean add(int newPosition, T newEntry);
  
  public boolean addToStart(T newEntry);

  /**
   * Task: Removes the entry at a given position from the list. Entries
   * originally at positions higher than the given position are at the next
   * lower position within the list, and the list's size is decreased by 1.
   *
   * @param givenPosition an integer that indicates the position of the entry to
   * be removed
   * @return a reference to the removed entry or null, if either the list was
   * empty, givenPosition < 1, or
   *          givenPosition > getNumberOfEntries()
   */
  public T remove(int givenPosition);
  
  public T remove(T anEntry);
  
  public T removeFirst();
  
  public T removeLast();

  /**
   * Task: Removes all entries from the list.
   */
  public void clear();

  /**
   * Task: Replaces the entry at a given position in the list.
   *
   * @param givenPosition an integer that indicates the position of the entry to
   * be replaced
   * @param newEntry the object that will replace the entry at the position
   * givenPosition
   * @return true if the replacement occurs, or false if either the list is
   * empty, givenPosition < 1, or givenPosition > getNumberOfEntries()
   */
  public boolean replace(int givenPosition, T newEntry);
  
  public boolean replace(T anEntry, T newEntry);
  
  public boolean replaceFirst(T newEntry);
  
  public boolean replaceLast(T newEntry);
  /**
   * Task: Retrieves the entry at a given position in the list.
   *
   * @param givenPosition an integer that indicates the position of the desired
   * entry
   * @return a reference to the indicated entry or null, if either the list is
   * empty, givenPosition < 1, or givenPosition > getNumberOfEntries()
   */
  public T getEntry(int givenPosition);
  

  public T getEntry(T anEntry);
  
  public T getFirst();
  
  public T getLast();

  /**
   * Task: Sees whether the list contains a given entry.
   *
   * @param anEntry the object that is the desired entry
   * @return true if the list contains anEntry, or false if not
   */
  public boolean contains(T anEntry);

  /**
   * Task: Gets the number of entries in the list.
   *
   * @return the integer number of entries currently in the list
   */
  public int getNumberOfEntries();

  /**
   * Task: Sees whether the list is empty.
   *
   * @return true if the list is empty, or false if not
   */
  public boolean isEmpty();

  /**
   * Task: Sees whether the list is full.
   *
   * @return true if the list is full, or false if not
   */
  public boolean isFull();
}